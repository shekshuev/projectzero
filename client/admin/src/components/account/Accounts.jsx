import React, { useState } from 'react';
import { Button, Container } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { useSelector } from "react-redux";
import {Link} from "react-router-dom";

const Accounts = () => {

    const [ columns ] = useState([
        { field: "id", headerName: "ID", flex: 1 },
        { field: "username", headerName: "Username" },
        { field: "firstName", headerName: "First name" },
        { field: "lastName", headerName: "Last Name" }
    ]);
    const [selectedAccountId, setSelectedAccountId] = useState();


    const selectionModelChanged = (ids) => {
        setSelectedAccountId(ids[0]);
    }

    const accounts = useSelector(state => state.account.accounts);

    return (
        <Container>
            <DataGrid  columns={columns}
                      rows={accounts}
                      autoHeight
                      selectionModel={selectedAccountId}
                      onSelectionModelChange={selectionModelChanged}
            />
            <Button sx={{my: 2}}
                    component={Link}
                    to={ `/account/${selectedAccountId}` }
                    variant="contained"
                    disabled={!selectedAccountId}>
                Создать пользователя
            </Button>


        </Container>
    );
};

export default Accounts;