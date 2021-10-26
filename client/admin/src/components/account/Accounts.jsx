import React, { useState } from 'react';
import { Button, Container, Paper, CircularProgress,
    TableContainer, Table, TableHead, TableBody, TableFooter, TableRow, TableCell, TablePagination } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { loadAccounts } from "../../api/account";
import { setAccountsPageAction, setAccountsCountAction } from "../../store/reducers/accountReducer";

const Accounts = () => {
    const dispatch = useDispatch();
    const { t } = useTranslation();

    const [selectedAccount, setSelectedAccount] = useState();

    const accounts = useSelector(state => state.account.accounts);
    const total = useSelector(state => state.account.totalCount);
    const count = useSelector(state => state.account.count);
    const page = useSelector(state => state.account.page);
    const loading = useSelector(state => state.account.loading);

    const onTableRowClick = (account) => {
        setSelectedAccount(account);
    }

    const onPageChanged = (e, page) => {
        dispatch(setAccountsPageAction(page));
        dispatch(loadAccounts(count, page * count));
    }

    const onRowsPerPageChanged = (e) => {
        dispatch(setAccountsPageAction(0));
        dispatch(setAccountsCountAction(e.target.value));
        dispatch(loadAccounts(e.target.value, 0));
    }

    return (
        <Container>
            <TableContainer component={Paper} sx={{mt: 5}}>
                <Table>
                    <caption>{ t("account:table:caption") }</caption>
                    <TableHead>
                        <TableRow>
                            <TableCell>{ t("account:table:headers:id") }</TableCell>
                            <TableCell>{ t("account:table:headers:username") }</TableCell>
                            <TableCell>{ t("account:table:headers:firstname") }</TableCell>
                            <TableCell>{ t("account:table:headers:lastname") }</TableCell>
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
                                    accounts.map((account) => (
                                        <TableRow hover
                                                  selected={selectedAccount?.id === account.id}
                                                  key={account.id}
                                                  onClick={() => onTableRowClick(account)}>
                                            <TableCell>{ account.id }</TableCell>
                                            <TableCell>{ account.username }</TableCell>
                                            <TableCell>{ account.firstName }</TableCell>
                                            <TableCell>{ account.lastName }</TableCell>
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
                    to="/account/new"
                    variant="contained">
                { t("account:createUserButton") }
            </Button>
            <Button sx={{my: 2, ml: 2}}
                    component={Link}
                    to={ `/account/${selectedAccount?.id}` }
                    variant="outlined"
                    disabled={!selectedAccount?.id}>
                { t("account:editUserButton") }
            </Button>
        </Container>
    );
};

export default Accounts;