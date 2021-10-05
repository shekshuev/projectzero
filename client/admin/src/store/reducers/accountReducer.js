const initialState = {
    error: "",
    loading: false,
    accounts: []
};

const SET_ACCOUNTS = "SET_ACCOUNTS";
const SET_ACCOUNTS_LOADING = "SET_ACCOUNTS_LOADING";
const SET_ACCOUNTS_ERROR = "SET_ACCOUNTS_ERROR";
const ADD_ACCOUNT = "ADD_ACCOUNT";

export const accountReducer = (state = initialState, action) => {
    switch (action.type){
        case SET_ACCOUNTS:
            return { ...state, accounts: action.payload };
        case SET_ACCOUNTS_LOADING:
            return { ...state, loading: action.payload };
        case SET_ACCOUNTS_ERROR:
            return { ...state, error: action.payload };
        case ADD_ACCOUNT:
            return { ...state, accounts: [...state.accounts, action.payload] }
        default:
            return state;
    }
}

export const setAccountsAction = (payload) => ({ type: SET_ACCOUNTS, payload });
export const setAccountsLoadingAction = (payload) => ({ type: SET_ACCOUNTS_LOADING, payload });
export const setAccountsErrorAction = (payload) => ({ type: SET_ACCOUNTS_ERROR, payload });
export const addAccountAction = (payload) => ({ type: ADD_ACCOUNT, payload });
