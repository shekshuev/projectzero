import React, { useEffect, useState } from 'react';
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { AppBar, IconButton, Toolbar, Typography, Drawer,
    MenuList, MenuItem, ListItemIcon, ListItemText } from "@material-ui/core";
import { AccountCircle, ListAlt, StackedBarChart, Menu } from "@mui/icons-material"
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Accounts from "./components/account/Accounts";
import Account from "./components/account/Account";
import Researches from "./components/research/Researches";
import Research from "./components/research/Research";
import Surveys from "./components/survey/Surveys";
import { loadAccounts } from "./api/account";
import { loadResearches } from "./api/research";
import { useTranslation } from "react-i18next";

const App = () => {

    const { t } = useTranslation();

    const dispatch = useDispatch();

    const accountCount = useSelector(state => state.account.count);
    const accountPage = useSelector(state => state.account.page);
    const researchCount = useSelector(state => state.research.count);
    const researchPage = useSelector(state => state.research.page);

    const [drawer, setDrawer] = useState(false);

    useEffect(() => {
        dispatch(loadAccounts(accountCount, accountPage * accountCount));
        dispatch(loadResearches(researchCount, researchPage * researchCount))
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
                        <Menu />
                    </IconButton>
                    <Typography variant="h6" component="div">
                        { t("common:header") }
                    </Typography>
                </Toolbar>
            </AppBar>
            <Router>
                <Drawer anchor="left"
                        open={drawer}
                        onClose={() => setDrawer(false)}
                        onClick={() => setDrawer(false)}>
                    <MenuList>
                        <MenuItem component={Link}
                                  to="/accounts">
                            <ListItemIcon>
                                <AccountCircle />
                            </ListItemIcon>
                            <ListItemText>{ t("app:menu:accounts") }</ListItemText>
                        </MenuItem>
                        <MenuItem component={Link}
                                  to="/researches">
                            <ListItemIcon>
                                <StackedBarChart />
                            </ListItemIcon>
                            <ListItemText>{ t("app:menu:researches") }</ListItemText>
                        </MenuItem>
                        <MenuItem component={Link}
                                  to="/surveys">
                            <ListItemIcon>
                                <ListAlt />
                            </ListItemIcon>
                            <ListItemText>{ t("app:menu:surveys") }</ListItemText>
                        </MenuItem>
                    </MenuList>
                </Drawer>
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
                    <Route path="/researches">
                        <Researches />
                    </Route>
                    <Route path="/research/new">
                        <Research />
                    </Route>
                    <Route path="/research/:id">
                        <Research />
                    </Route>
                    <Route path="/surveys">
                        <Surveys />
                    </Route>
                </Switch>
            </Router>
        </React.StrictMode>
    );
};

export default App;