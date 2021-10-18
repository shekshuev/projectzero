import React, { useState } from 'react';
import { useDispatch, useSelector } from "react-redux";
import { useTranslation } from "react-i18next";
import { setResearchesPageAction } from "../../store/reducers/researchReducer";
import { loadResearches } from "../../api/research";
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
import {Link} from "react-router-dom";

const Researches = () => {
    const dispatch = useDispatch();
    const { t } = useTranslation();

    const [selectedResearch, setSelectedResearch] = useState();

    const researches = useSelector(state => state.research.researches);
    const total = useSelector(state => state.research.totalCount);
    const count = useSelector(state => state.research.count);
    const page = useSelector(state => state.research.page);
    const loading = useSelector(state => state.research.loading);

    const onTableRowClick = (research) => {
        setSelectedResearch(research);
    }

    const onPageChanged = (e, page) => {
        dispatch(setResearchesPageAction(page));
        loadResearches(count, page * count);
    }

    return (
        <Container>
            <TableContainer component={Paper} sx={{mt: 5}}>
                <Table>
                    <caption>{ t("research:table:caption") }</caption>
                    <TableHead>
                        <TableRow>
                            <TableCell>{ t("research:table:headers:id") }</TableCell>
                            <TableCell>{ t("research:table:headers:title") }</TableCell>
                            <TableCell>{ t("research:table:headers:beginDate") }</TableCell>
                            <TableCell>{ t("research:table:headers:endDate") }</TableCell>
                        </TableRow>
                    </TableHead>
                    {
                        loading ?
                            <TableBody>
                                <TableRow>
                                    <TableCell colSpan={4} align={"center"}>
                                        <CircularProgress />
                                    </TableCell>
                                </TableRow>
                            </TableBody> :
                            <TableBody>
                                {
                                    researches.map((research) => (
                                        <TableRow hover
                                                  selected={selectedResearch?.id === research.id}
                                                  key={research.id}
                                                  onClick={() => onTableRowClick(research)}>
                                            <TableCell>{ research.id }</TableCell>
                                            <TableCell>{ research.title }</TableCell>
                                            <TableCell>{ research.beginDate }</TableCell>
                                            <TableCell>{ research.endDate }</TableCell>
                                        </TableRow>
                                    ))
                                }
                            </TableBody>
                    }
                    {
                        loading ? "" :
                            <TableFooter>
                                <TableRow>
                                    <TablePagination count={total}
                                                     page={page}
                                                     onPageChange={onPageChanged}
                                                     rowsPerPage={count} />
                                </TableRow>
                            </TableFooter>
                    }
                </Table>
            </TableContainer>
            <Button sx={{my: 2}}
                    component={Link}
                    to="/research/new"
                    variant="contained">
                { t("research:createResearchButton") }
            </Button>
            <Button sx={{my: 2, ml: 2}}
                    component={Link}
                    to={ `/research/${selectedResearch?.id}` }
                    variant="outlined"
                    disabled={!selectedResearch?.id}>
                { t("research:editResearchButton") }
            </Button>
        </Container>
    );
};

export default Researches;