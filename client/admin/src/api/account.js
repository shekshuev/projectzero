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
            dispatch(setAccountsLoadingAction(false));
        }
    }
};

export const loadAccount = async (id) => {
    try {
        let result = await axios.get(`${url}/${id}`);
        return result.data;
    } catch (e) {
        return {
            data: e.message,
            success: false
        }
    }
};

export const createAccount = (account) => {
    return async dispatch => {
        dispatch(setAccountsErrorAction(""));
        dispatch(setAccountsLoadingAction(true));
        try {
            const result = await axios.post(url, JSON.stringify(account), {
                headers: {
                    'Accept': 'application/json',
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