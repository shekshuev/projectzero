import React, { useState } from 'react';
import { Button, Container, Paper, CircularProgress,
    TableContainer, Table, TableHead, TableBody, TableFooter, TableRow, TableCell, TablePagination } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { loadAccounts } from "../../api/account";
import { setAccountsPageAction } from "../../store/reducers/accountReducer";

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
        loadAccounts(count, page * count);
    }

    return (
        <Container>
            <TableContainer component={Paper} sx={{mt: 5}}>
                <Table>
                    <caption>A basic table example with a caption</caption>
                    <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Username</TableCell>
                            <TableCell>First Name</TableCell>
                            <TableCell>Last Name</TableCell>
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