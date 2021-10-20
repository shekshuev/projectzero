import React, { useState } from 'react';
import { useTranslation } from "react-i18next";
import { Link, useHistory, useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { Container, Grid, Button, Dialog, DialogContent, DialogTitle, DialogActions } from "@mui/material";
import CustomTextField from "../ui/CustomTextField";
import useSurvey from "../../hooks/useSurvey";
import { createSurvey, updateSurvey, removeSurvey } from "../../api/survey";
import LocalizationProvider from "@mui/lab/LocalizationProvider";
import AdapterDateFns from "@mui/lab/AdapterDateFns";
import DatePicker from "@mui/lab/DatePicker";
import LoadingButton from "@mui/lab/LoadingButton";

const Survey = () => {
    const { t } = useTranslation();
    const { id } = useParams();
    const dispatch = useDispatch();

    const [ dialogOpen, setDialogOpen ] = useState(false);

    const loading = useSelector(state => state.survey.loading);
    const count = useSelector(state => state.survey.count);
    const page = useSelector(state => state.survey.page);
    const [survey, setSurvey] = useSurvey(id);

    const history = useHistory();

    const createOrUpdateSurveyAsync = (id, survey, dispatch) => new Promise((resolve, reject) => {
        if (id) {
            dispatch(updateSurvey(survey));
        } else {
            dispatch(createSurvey(survey));
        }
        resolve();
    });

    const createOrUpdateSurvey = () => {
        createOrUpdateSurveyAsync(id, survey, dispatch).then(() => history.replace("/surveys"));
    };

    const deleteSurveyAsync = (survey, page, count, dispatch) => new Promise((resolve, reject) => {
        dispatch(removeSurvey(survey, count, page * count));
        resolve();
    });

    const deleteSurvey = () => {
        deleteSurveyAsync(survey, page, count, dispatch).then(() => history.replace("/surveys"));
    };

    return (
        <Container sx={{mt:2}}>
            <Grid spacing={2}
                  container
                  direction="column"
                  alignItems="center">
                <Grid item>
                    <CustomTextField fullWidth
                                     value={survey?.title ?? ""}
                                     onChange={e=>setSurvey({...survey, title: e.target.value})}
                                     label={t("survey:title")}
                                     disabled={loading}/>
                </Grid>
                <Grid item>
                    <CustomTextField fullWidth
                                     value={survey?.description ?? ""}
                                     onChange={e=>setSurvey({...survey, description: e.target.value})}
                                     label={t("survey:description")}
                                     disabled={loading}/>
                </Grid>
                <Grid item>
                    <LoadingButton variant="contained"
                                   loading={loading}
                                   onClick={createOrUpdateSurvey}>
                        { id ? t("survey:updateSurveyButton") : t("survey:createSurveyButton") }
                    </LoadingButton>
                </Grid>
                <Grid item>
                    <Button variant="outlined"
                            component={Link}
                            to="/surveys"
                            replace
                            disabled={loading}>{ t("common:cancel") }</Button>
                </Grid>
                <Grid item>
                    <Button variant="contained"
                            color="error"
                            onClick={() => setDialogOpen(true)}
                            disabled={loading}>{ t("survey:removeSurveyButton") }</Button>
                </Grid>
            </Grid>
            <Dialog open={dialogOpen}>
                <DialogTitle>{ t("common:header") }</DialogTitle>
                <DialogContent>{ t("survey:removeSurveyDialogMessage") }</DialogContent>
                <DialogActions>
                    <Button autoFocus
                            onClick={() => setDialogOpen(false)}>{ t("common:cancel") }</Button>
                    <Button onClick={deleteSurvey}
                            color="error">{ t("common:yes") }</Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
};

export default Survey;