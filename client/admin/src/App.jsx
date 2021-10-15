import React, {useEffect, useState} from 'react';
import { useDispatch, useSelector } from "react-redux";
import { AppBar, IconButton, Toolbar, Typography, Drawer,
    MenuList, MenuItem, ListItemIcon, ListItemText } from "@material-ui/core";
import { AccountCircle } from "@material-ui/icons"
import MenuIcon from "@material-ui/icons/Menu";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Accounts from "./components/account/Accounts";
import Account from "./components/account/Account";
import { loadAccounts } from "./api/account";
import { useTranslation } from "react-i18next";

const App = () => {

    const { t } = useTranslation();

    const dispatch = useDispatch();

    const count = useSelector(state => state.account.count);
    const page = useSelector(state => state.account.page);

    const [drawer, setDrawer] = useState(false);

    useEffect(() => {
        dispatch(loadAccounts(count, page * count));
    }, []);
    return (
        <React.StrictMode>
            <AppBar position="static">
                <Toolbar>
                    <IconButton size="large"
                                edge="start"
                                color="inherit"
                                aria-label="menu"
                                onClick={() => setDrawer(true)}
                                sx={{ mr: 2 }}>
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6" component="div">
                        { t("common:header") }
                    </Typography>
                </Toolbar>
                <Drawer anchor="left" open={drawer} onClose={() => setDrawer(false)}>
                    <MenuList>
                        <MenuItem>
                            <ListItemIcon>
                                <AccountCircle />
                            </ListItemIcon>
                            <ListItemText>{ t("app:menu:accounts") }</ListItemText>
                        </MenuItem>
                    </MenuList>
                </Drawer>
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