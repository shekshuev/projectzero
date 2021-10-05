import React, {useState} from 'react';
import { useTranslation } from "react-i18next";
import { Container, TextField, Grid, Button, Select, MenuItem, FormControl, InputLabel } from "@mui/material";
import { createAccount } from "../../api/account";
import { useDispatch } from "react-redux";

const CustomTextField = (props) => {
    return (
        <TextField sx={{ width: "300px" }}  {...props}/>
    )
}

const Account = () => {
    const { t } = useTranslation();
    const dispatch = useDispatch();

    const createUser = () => {
        dispatch(createAccount(account));
    }

    const [ roles ] = useState([
        "user",
        "admin"
    ]);

    const [ account, setAccount ] = useState({
        id: "",
        username: "",
        password: "",
        confirmPassword: "",
        firstName: "",
        lastName: "",
        surname: "",
        role: ""
    });

    return (
        <Container sx={{mt:2}}>
            <Grid spacing={2}
                  container
                  direction="column"
                  alignItems="center">
                <Grid item>
                    <CustomTextField fullWidth
                                     value={account.username}
                                     onChange={e=>setAccount({...account, username: e.target.value})}
                                     label={t("account:username")}
                                     variant="standard" />
                </Grid>
                <Grid item>
                    <CustomTextField fullWidth
                                     value={account.surname}
                                     onChange={e=>setAccount({...account, surname: e.target.value})}
                                     label={t("account:surname")}
                                     variant="standard" />
                </Grid>
                <Grid item>
                    <CustomTextField fullWidth
                                     value={account.firstName}
                                     onChange={e=>setAccount({...account, firstName: e.target.value})}
                                     label={t("account:firstName")}
                                     variant="standard" />
                </Grid>
                <Grid item>
                    <CustomTextField fullWidth
                                     value={account.lastName}
                                     onChange={e=>setAccount({...account, lastName: e.target.value})}
                                     label={t("account:lastName")}
                                     variant="standard" />
                </Grid>
                <Grid item>
                    <CustomTextField fullWidth
                                     value={account.password}
                                     onChange={e=>setAccount({...account, password: e.target.value})}
                                     label={t("account:password")}
                                     type="password"
                                     variant="standard" />
                </Grid>
                <Grid item>
                    <CustomTextField fullWidth
                                     value={account.confirmPassword}
                                     onChange={e=>setAccount({...account, confirmPassword: e.target.value})}
                                     label={t("account:confirmPassword")}
                                     type="password"
                                     variant="standard" />
                </Grid>
                <Grid item>
                    <FormControl variant="standard" sx={{ width: "300px" }}>
                        <InputLabel>{t("account:role")}</InputLabel>
                        <Select fullWidth
                                value={account.role}
                                onChange={e=>setAccount({...account, role: e.target.value})}
                                label={t("account:role")}>
                            {
                                roles.map(role => {
                                    return <MenuItem value={role} key={role}>{role}</MenuItem>
                                })

                            }
                        </Select>
                    </FormControl>

                </Grid>
                <Grid item>
                    <Button variant="contained"
                            onClick={createUser}>{t("account:createUserButton")}</Button>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Account;