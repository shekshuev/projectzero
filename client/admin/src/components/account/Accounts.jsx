import React, { useState } from 'react';
import { Button, Container } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { loadAccounts } from "../../api/account";
import { setAccountsPageAction } from "../../store/reducers/accountReducer";

const Accounts = () => {
    const dispatch = useDispatch();
    const { t } = useTranslation();

    const [ columns ] = useState([
        { field: "id", headerName: "ID", flex: 1 },
        { field: "username", headerName: "Username" },
        { field: "firstName", headerName: "First name" },
        { field: "lastName", headerName: "Last Name" }
    ]);
    const [selectedAccountId, setSelectedAccountId] = useState();

    const accounts = useSelector(state => state.account.accounts);
    const total = useSelector(state => state.account.totalCount);
    const count = useSelector(state => state.account.count);
    const page = useSelector(state => state.account.page);
    const loading = useSelector(state => state.account.loading);

    const selectionModelChanged = (ids) => {
        setSelectedAccountId(ids[0]);
    }

    const onPageChanged = (page) => {
        dispatch(setAccountsPageAction(page));
        loadAccounts(count, page * count);
    }

    return (
        <Container>
            <DataGrid columns={columns}
                      rows={accounts}
                      pageSize={count}
                      page={page}
                      rowCount={total}
                      autoHeight
                      paginationMode="server"
                      selectionModel={selectedAccountId}
                      onSelectionModelChange={selectionModelChanged}
                      onPageChange={onPageChanged}
                      loading={loading}
            />
            <Button sx={{my: 2}}
                    component={Link}
                    to="/account/new"
                    variant="contained">
                { t("account:createUserButton") }
            </Button>
            <Button sx={{my: 2, ml: 2}}
                    component={Link}
                    to={ `/account/${selectedAccountId}` }
                    variant="outlined"
                    disabled={!selectedAccountId}>
                { t("account:editUserButton") }
            </Button>
        </Container>
    );
};

export default Accounts;