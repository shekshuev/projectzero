import React, { useState } from 'react';
import { useTranslation } from "react-i18next";
import { Container, TextField, Grid, Button, Select, MenuItem, FormControl, InputLabel,
    Dialog, DialogContent, DialogTitle, DialogActions } from "@mui/material";
import LoadingButton from '@mui/lab/LoadingButton';
import { createAccount, updateAccount, removeAccount } from "../../api/account";
import { useDispatch, useSelector } from "react-redux";
import {useHistory, useParams} from "react-router-dom";
import { Link } from "react-router-dom";
import useAccount from "../../hooks/useAccount";

const CustomTextField = (props) => {
    return (
        <TextField sx={{ width: "300px" }}  {...props}/>
    )
}

const Account = () => {
    const { t } = useTranslation();
    const { id } = useParams();
    const dispatch = useDispatch();
    const [ roles ] = useState([
        "user",
        "admin"
    ]);
    const [ dialogOpen, setDialogOpen ] = useState(false);
    const loading = useSelector(state => state.account.loading);
    const count = useSelector(state => state.account.count);
    const page = useSelector(state => state.account.page);
    const [account, setAccount] = useAccount(id);

    const history = useHistory();


    const createOrUpdateUserAsync = (id, account, dispatch) => new Promise((resolve, reject) => {
        if (id) {
            dispatch(updateAccount(account));
        } else {
            dispatch(createAccount(account));
        }
        resolve();
    })

    const createOrUpdateUser = () => {
        createOrUpdateUserAsync(id, account, dispatch).then(() => history.replace("/accounts"));
    }

    const removeUser = () => {
        removeUserAsync(account, dispatch).then(() => history.replace("/accounts"));
    }

    const removeUserAsync = (account, dispatch) => new Promise((resolve, reject) => {
        dispatch(removeAccount(account, page, page * count));
        resolve();
    })

    return (
        <Container sx={{mt:2}}>
            <Grid spacing={2}
                  container
                  direction="column"
                  alignItems="center">
                <Grid item>
                    <CustomTextField fullWidth
                                     value={account?.username ?? ""}
                                     onChange={e=>setAccount({...account, username: e.target.value})}
                                     label={t("account:username")}
                                     variant="standard"
                                     disabled={loading}/>
                </Grid>
                <Grid item>
                    <CustomTextField fullWidth
                                     value={account?.surname ?? ""}
                                     onChange={e=>setAccount({...account, surname: e.target.value})}
                                     label={t("account:surname")}
                                     variant="standard"
                                     disabled={loading}/>
                </Grid>
                <Grid item>
                    <CustomTextField fullWidth
                                     value={account?.firstName ?? ""}
                                     onChange={e=>setAccount({...account, firstName: e.target.value})}
                                     label={t("account:firstName")}
                                     variant="standard"
                                     disabled={loading}/>
                </Grid>
                <Grid item>
                    <CustomTextField fullWidth
                                     value={account?.lastName ?? ""}
                                     onChange={e=>setAccount({...account, lastName: e.target.value})}
                                     label={t("account:lastName")}
                                     variant="standard"
                                     disabled={loading}/>
                </Grid>
                <Grid item>
                    <CustomTextField fullWidth
                                     value={account?.password ?? ""}
                                     onChange={e=>setAccount({...account, password: e.target.value})}
                                     label={t("account:password")}
                                     type="password"
                                     variant="standard"
                                     disabled={loading}/>
                </Grid>
                <Grid item>
                    <CustomTextField fullWidth
                                     value={account?.confirmPassword ?? ""}
                                     onChange={e=>setAccount({...account, confirmPassword: e.target.value})}
                                     label={t("account:confirmPassword")}
                                     type="password"
                                     variant="standard"
                                     disabled={loading}/>
                </Grid>
                <Grid item>
                    <FormControl variant="standard" sx={{ width: "300px" }}>
                        <InputLabel>{t("account:role")}</InputLabel>
                        <Select fullWidth
                                value={account?.role ?? ""}
                                onChange={e=>setAccount({...account, role: e.target.value})}
                                label={t("account:role")}
                                disabled={loading}>
                            {
                                roles.map(role => {
                                    return <MenuItem value={role} key={role}>{role}</MenuItem>
                                })
                            }
                        </Select>
                    </FormControl>

                </Grid>
                <Grid item>
                    <LoadingButton variant="contained"
                                   loading={loading}
                                   onClick={createOrUpdateUser}>
                        { id ? t("account:updateUserButton") : t("account:createUserButton") }
                    </LoadingButton>
                </Grid>
                <Grid item>
                    <Button variant="outlined"
                            component={Link}
                            to="/accounts"
                            replace
                            disabled={loading}>{ t("common:cancel") }</Button>
                </Grid>
                <Grid item>
                    <Button variant="contained"
                            color="error"
                            onClick={() => setDialogOpen(true)}
                            disabled={loading}>{ t("account:removeUserButton") }</Button>
                </Grid>
            </Grid>
            <Dialog open={dialogOpen}>
                <DialogTitle>{ t("common:header") }</DialogTitle>
                <DialogContent>{ t("account:removeUserDialogMessage") }</DialogContent>
                <DialogActions>
                    <Button autoFocus
                            onClick={() => setDialogOpen(false)}>{ t("common:cancel") }</Button>
                    <Button onClick={removeUser}
                            color="error">{ t("common:yes") }</Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
};

export default Account;