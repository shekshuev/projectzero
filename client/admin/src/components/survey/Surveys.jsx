import React, { useState } from 'react';
import { useDispatch, useSelector } from "react-redux";
import { useTranslation } from "react-i18next";
import {
    Button,
    CircularProgress, Container,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableFooter,
    TableHead, TablePagination,
    TableRow
} from "@mui/material";
import { setSurveysPageAction, setSurveysCountAction } from "../../store/reducers/surveyReducer";
import { loadSurveys } from "../../api/survey";
import { Link } from "react-router-dom";

const Surveys = () => {
    const dispatch = useDispatch();
    const { t } = useTranslation();

    const [selectedSurvey, setSelectedSurvey] = useState();

    const surveys = useSelector(state => state.survey.surveys);
    const total = useSelector(state => state.survey.totalCount);
    const count = useSelector(state => state.survey.count);
    const page = useSelector(state => state.survey.page);
    const loading = useSelector(state => state.survey.loading);

    const onTableRowClick = (survey) => {
        setSelectedSurvey(survey);
    }

    const onPageChanged = (e, page) => {
        dispatch(setSurveysPageAction(page));
        dispatch(loadSurveys(count, page * count));
    }

    const onRowsPerPageChanged = (e) => {
        dispatch(setSurveysPageAction(0));
        dispatch(setSurveysCountAction(e.target.value));
        dispatch(loadSurveys(e.target.value, 0));
    }

    return (
        <Container>
            <TableContainer component={Paper} sx={{mt: 5}}>
                <Table>
                    <caption>{ t("survey:table:caption") }</caption>
                    <TableHead>
                        <TableRow>
                            <TableCell>{ t("survey:table:headers:id") }</TableCell>
                            <TableCell>{ t("survey:table:headers:title") }</TableCell>
                            <TableCell>{ t("survey:table:headers:questionCount") }</TableCell>
                        </TableRow>
                    </TableHead>
                    {
                        loading ?
                            <TableBody>
                                <TableRow>
                                    <TableCell colSpan={3} align={"center"}>
                                        <CircularProgress />
                                    </TableCell>
                                </TableRow>
                            </TableBody> :
                            <TableBody>
                                {
                                    surveys.map((survey) => (
                                        <TableRow hover
                                                  selected={selectedSurvey?.id === survey.id}
                                                  key={survey.id}
                                                  onClick={() => onTableRowClick(survey)}>
                                            <TableCell>{ survey.id }</TableCell>
                                            <TableCell>{ survey.title }</TableCell>
                                            <TableCell>{ survey.questions?.length ?? 0 }</TableCell>
                                        </TableRow>
                                    ))
                                }
                            </TableBody>
                    }
                    {
                        loading ? null :
                            <TableFooter>
                                <TableRow>
                                    <TablePagination count={total}
                                                     page={page}
                                                     rowsPerPageOptions={[5,10,25,]}
                                                     onRowsPerPageChange={onRowsPerPageChanged}
                                                     onPageChange={onPageChanged}
                                                     rowsPerPage={count} />
                                </TableRow>
                            </TableFooter>
                    }
                </Table>
            </TableContainer>
            <Button sx={{my: 2}}
                    component={Link}
                    to="/survey/new"
                    variant="contained">
                { t("survey:createSurveyButton") }
            </Button>
            <Button sx={{my: 2, ml: 2}}
                    component={Link}
                    to={ `/survey/${selectedSurvey?.id}` }
                    variant="outlined"
                    disabled={!selectedSurvey?.id}>
                { t("survey:editSurveyButton") }
            </Button>
        </Container>
    );
};

export default Surveys;