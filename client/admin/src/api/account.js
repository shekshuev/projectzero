import axios from "axios";
import { API_URL } from "./base";
import { setAccountsAction,
    setAccountsLoadingAction,
    setAccountsErrorAction,
    addAccountAction,
    updateAccountAction,
    setAccountsTotalCountAction } from "../store/reducers/accountReducer";

const url = `${API_URL()}account`;

export const loadAccounts = (count, offset) => {
    return async dispatch => {
        dispatch(setAccountsErrorAction(""));
        dispatch(setAccountsLoadingAction(true));
        try {
            let result = await axios.get(url, {
                params: { count, offset }
            });
            if (result.data.success) {
                dispatch(setAccountsTotalCountAction(result.data.payload.total));
                dispatch(setAccountsAction(result.data.payload.accounts));
            } else {
                dispatch(setAccountsErrorAction(result.data.payload.message));
                dispatch(setAccountsAction([]));
                dispatch(setAccountsTotalCountAction(0));
            }
        } catch (e) {
            dispatch(setAccountsErrorAction(e.message));
            dispatch(setAccountsAction([]));
            dispatch(setAccountsTotalCountAction(0));
        } finally {
            dispatch(setAccountsLoadingAction(false));
        }
    };
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
                dispatch(addAccountAction(result.data.payload));
            } else {
                dispatch(setAccountsErrorAction(result.data.payload));
            }
        } catch (e) {
            dispatch(setAccountsErrorAction(e.message));
        } finally {
            dispatch(setAccountsLoadingAction(false));
        }
    };
}

export const updateAccount = (account) => {
    return async dispatch => {
        dispatch(setAccountsErrorAction(""));
        dispatch(setAccountsLoadingAction(true));
        try {
            const result = await axios.put(`${url}/${account.id}`, JSON.stringify(account), {
                headers: {
                    'Accept': 'application/json',
                    "Content-Type": "application/json"
                }
            });
            if (result.data.success) {
                dispatch(updateAccountAction(result.data.payload));
            } else {
                dispatch(setAccountsErrorAction(result.data.payload));
            }
        } catch (e) {
            dispatch(setAccountsErrorAction(e.message));
        } finally {
            dispatch(setAccountsLoadingAction(false));
        }
    };
}

export const removeAccount = (account, count, offset) => {
    return async dispatch => {
        dispatch(setAccountsErrorAction(""));
        dispatch(setAccountsLoadingAction(true));
        try {
            const result = await axios.delete(`${url}/${account.id}`);
            if (result.data.success) {
                dispatch(loadAccounts(count, offset));
            } else {
                dispatch(setAccountsErrorAction(result.data.payload));
            }
        } catch (e) {
            dispatch(setAccountsErrorAction(e.message));
        } finally {
            dispatch(setAccountsLoadingAction(false));
        }
    };
}