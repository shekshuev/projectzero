import axios from "axios";
import { API_URL } from "./base";

const url = `${API_URL()}account`;

import { setAccountsAction,
    setAccountsLoadingAction,
    setAccountsErrorAction,
    addAccountAction } from "../store/reducers/accountReducer";

export const loadAccounts = () => {
    return async dispatch => {
        dispatch(setAccountsErrorAction(""));
        dispatch(setAccountsLoadingAction(true));
        try {
            let result = await axios.get(url);
            if (result.data.success) {
                dispatch(setAccountsAction(result.data.payload));
            } else {
                dispatch(setAccountsErrorAction(result.data.payload.message));
                dispatch(setAccountsAction([]));
            }
        } catch (e) {
            dispatch(setAccountsErrorAction(e.message));
            dispatch(setAccountsAction([]));
        } finally {
            dispatch(setAccountsLoadingAction(true));
        }
    }
};

export const createAccount = (account) => {
    return async dispatch => {
        dispatch(setAccountsErrorAction(""));
        dispatch(setAccountsLoadingAction(true));
        try {
            let form = new FormData();
            form.append("account", JSON.stringify(account));
            const result = await axios.post(url, form, {
                headers: {
                    "Content-Type": "application/json"
                }
            });
            if (result.data.success) {
                dispatch(addAccountAction(result.data.payload.account));
            } else {
                dispatch(setAccountsErrorAction(result.data.payload.message));
            }
        } catch (e) {
            dispatch(setAccountsErrorAction(e.message));
        } finally {
            dispatch(setAccountsLoadingAction(false));
        }
    }
}