import React, { useEffect, useState } from 'react';
import { useTranslation } from "react-i18next";
import { Container, TextField, Grid, Button, Select, MenuItem, FormControl, InputLabel } from "@mui/material";
import LoadingButton from '@mui/lab/LoadingButton';
import { createAccount } from "../../api/account";
import { useDispatch, useSelector } from "react-redux";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { useForm, Controller } from "react-hook-form";

const CustomTextField = (props) => {
    return (
        <TextField sx={{ width: "300px" }}  {...props}/>
    )
}

const schema = yup.object({
    username: yup.string().required()
}).required();

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


    const loading = useSelector(state => state.account.loading);


    const { register, handleSubmit, watch, setValue, formState: { errors }} = useForm({
        resolver: yupResolver(schema)
    });
    const onSubmit = data => {
        console.log(data);
    }

    useEffect(() => {
        register("username");
    }, [register])

    const [ account, setAccount ] = useState({
        id: null,
        username: watch("username"),
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
                  component="form"
                  onSubmit={handleSubmit(onSubmit)}
                  container
                  direction="column"
                  alignItems="center">
                <Grid item>
                    <CustomTextField fullWidth
                                     error={ !!errors?.username }
                                     value={account.username}
                                     onChange={e=>setValue("username", e.target.value)}
                                     label={t("account:username")}
                                     helperText={ errors.username?.message }
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
                    <LoadingButton variant="contained"
                                   loading={loading}
                                   type="submit">{t("account:createUserButton")}</LoadingButton>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Account;