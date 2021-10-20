import React, { useState } from 'react';
import { Container, Grid, Button, Dialog, DialogContent, DialogTitle, DialogActions } from "@mui/material";
import DatePicker from '@mui/lab/DatePicker';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import LoadingButton from '@mui/lab/LoadingButton';
import CustomTextField from "../ui/CustomTextField";
import { useTranslation } from "react-i18next";
import { Link, useHistory, useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import useResearch from "../../hooks/useResearch";
import { updateResearch, createResearch, removeResearch } from "../../api/research";

const Research = () => {
    const { t } = useTranslation();
    const { id } = useParams();
    const dispatch = useDispatch();

    const [ dialogOpen, setDialogOpen ] = useState(false);

    const loading = useSelector(state => state.research.loading);
    const count = useSelector(state => state.research.count);
    const page = useSelector(state => state.research.page);
    const [research, setResearch] = useResearch(id);

    const history = useHistory();

    const createOrUpdateResearchAsync = (id, research, dispatch) => new Promise((resolve, reject) => {
        if (id) {
            dispatch(updateResearch(research));
        } else {
            dispatch(createResearch(research));
        }
        resolve();
    });

    const createOrUpdateResearch = () => {
        createOrUpdateResearchAsync(id, research, dispatch).then(() => history.replace("/researches"));
    };

    const deleteResearchAsync = (research, page, count, dispatch) => new Promise((resolve, reject) => {
        dispatch(removeResearch(research, count, page * count));
        resolve();
    });

    const deleteResearch = () => {
        deleteResearchAsync(research, page, count, dispatch).then(() => history.replace("/researches"));
    };

    return (
        <Container sx={{mt:2}}>
            <Grid spacing={2}
                  container
                  direction="column"
                  alignItems="center">
                <Grid item>
                    <CustomTextField fullWidth
                                     value={research?.title ?? ""}
                                     onChange={e=>setResearch({...research, title: e.target.value})}
                                     label={t("research:title")}
                                     variant="standard"
                                     disabled={loading}/>
                </Grid>
                <Grid item>
                    <CustomTextField fullWidth
                                     value={research?.description ?? ""}
                                     onChange={e=>setResearch({...research, description: e.target.value})}
                                     label={t("research:description")}
                                     variant="standard"
                                     disabled={loading}/>
                </Grid>
                <Grid item>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DatePicker value={research?.beginDate ?? null}
                                    label={t("research:beginDate")}
                                    onChange={date=>setResearch({...research, beginDate: date})}
                                    renderInput={(params) => <CustomTextField variant="standard" fullWidth {...params} />} />
                    </LocalizationProvider>
                </Grid>
                <Grid item>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DatePicker value={research?.endDate ?? null}
                                    label={t("research:endDate")}
                                    onChange={date=>setResearch({...research, endDate: date})}
                                    renderInput={(params) => <CustomTextField variant="standard" fullWidth {...params} />} />
                    </LocalizationProvider>
                </Grid>
                <Grid item>
                    <LoadingButton variant="contained"
                                   loading={loading}
                                   onClick={createOrUpdateResearch}>
                        { id ? t("research:updateResearchButton") : t("research:createResearchButton") }
                    </LoadingButton>
                </Grid>
                <Grid item>
                    <Button variant="outlined"
                            component={Link}
                            to="/researches"
                            replace
                            disabled={loading}>{ t("common:cancel") }</Button>
                </Grid>
                <Grid item>
                    <Button variant="contained"
                            color="error"
                            onClick={() => setDialogOpen(true)}
                            disabled={loading}>{ t("research:removeResearchButton") }</Button>
                </Grid>
            </Grid>
            <Dialog open={dialogOpen}>
                <DialogTitle>{ t("common:header") }</DialogTitle>
                <DialogContent>{ t("research:removeResearchDialogMessage") }</DialogContent>
                <DialogActions>
                    <Button autoFocus
                            onClick={() => setDialogOpen(false)}>{ t("common:cancel") }</Button>
                    <Button onClick={deleteResearch}
                            color="error">{ t("common:yes") }</Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
};

export default Research;