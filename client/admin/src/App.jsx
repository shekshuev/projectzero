import React, { useEffect } from 'react';
import { useDispatch } from "react-redux";
import { AppBar, IconButton, Toolbar, Typography } from "@material-ui/core";
import MenuIcon from "@material-ui/icons/Menu";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Accounts from "./components/account/Accounts";
import Account from "./components/account/Account";
import { loadAccounts } from "./api/account";
import { useTranslation } from "react-i18next";

const App = () => {

    const { t } = useTranslation();

    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(loadAccounts());
    }, null);
    return (
        <React.StrictMode>
            <AppBar position="static">
                <Toolbar>
                    <IconButton size="large"
                                edge="start"
                                color="inherit"
                                aria-label="menu"
                                sx={{ mr: 2 }}>
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6" component="div">
                        { t("common:header") }
                    </Typography>
                </Toolbar>
            </AppBar>
            <Router>
                <Switch>
                    <Route path="/accounts">
                        <Accounts />
                    </Route>
                    <Route path="/account/new">
                        <Account />
                    </Route>
                    <Route path="/account/:id">
                        <Account />
                    </Route>
                </Switch>
            </Router>
        </React.StrictMode>
    );
};

export default App;