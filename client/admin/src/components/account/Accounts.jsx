import React, { useState } from 'react';
import { Button, Container } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { useSelector } from "react-redux";

const Accounts = () => {

    const [ columns ] = useState([
        { field: "id", headerName: "ID", flex: 1 },
        { field: "username", headerName: "Username" },
        { field: "firstName", headerName: "First name" },
        { field: "lastName", headerName: "Last Name" }
    ]);

    const [selectedAccount, setSelectedAccount] = useState();


    const selectionModelChanged = (model) => {
        setSelectedAccount(model);
    }

    const accounts = useSelector(state => state.account.accounts);

    return (
        <Container>
            <DataGrid  columns={columns}
                      rows={accounts}
                      autoHeight
                      selectionModel={selectedAccount}
                      onSelectionModelChange={selectionModelChanged}
            />
            <Button sx={{my: 2}}
                    variant="contained"
                    disabled={!selectedAccount}>Создать пользователя</Button>
        </Container>
    );
};

export default Accounts;