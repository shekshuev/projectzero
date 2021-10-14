const initialState = {
    error: "",
    loading: false,
    accounts: [],
    count: 5,
    page: 0,
    totalCount: 0
};

const SET_ACCOUNTS = "SET_ACCOUNTS";
const SET_ACCOUNTS_LOADING = "SET_ACCOUNTS_LOADING";
const SET_ACCOUNTS_ERROR = "SET_ACCOUNTS_ERROR";
const SET_ACCOUNTS_COUNT = "SET_ACCOUNTS_COUNT";
const SET_ACCOUNTS_PAGE = "SET_ACCOUNTS_PAGE";
const SET_ACCOUNTS_TOTAL_COUNT = "SET_ACCOUNTS_TOTAL_COUNT";
const ADD_ACCOUNT = "ADD_ACCOUNT";
const UPDATE_ACCOUNT = "UPDATE_ACCOUNT";

export const accountReducer = (state = initialState, action) => {
    switch (action.type){
        case SET_ACCOUNTS:
            return { ...state, accounts: action.payload };
        case SET_ACCOUNTS_LOADING:
            return { ...state, loading: action.payload };
        case SET_ACCOUNTS_ERROR:
            return { ...state, error: action.payload };
        case SET_ACCOUNTS_COUNT:
            return { ...state, count: action.payload };
        case SET_ACCOUNTS_PAGE:
            return { ...state, page: action.payload };
        case SET_ACCOUNTS_TOTAL_COUNT:
            return { ...state, totalCount: action.payload};
        case ADD_ACCOUNT:
            return { ...state, accounts: [...state.accounts, action.payload] };
        case UPDATE_ACCOUNT:
            return { ...state, accounts: state.accounts.map(account => {
                if (account.id === action.payload.id) {
                    return action.payload;
                } else {
                    return account;
                }
            })};
        default:
            return state;
    }
}

export const setAccountsAction = (payload) => ({ type: SET_ACCOUNTS, payload });
export const setAccountsLoadingAction = (payload) => ({ type: SET_ACCOUNTS_LOADING, payload });
export const setAccountsErrorAction = (payload) => ({ type: SET_ACCOUNTS_ERROR, payload });
export const setAccountsCountAction = (payload) => ({ type: SET_ACCOUNTS_COUNT, payload });
export const setAccountsPageAction = (payload) => ({ type: SET_ACCOUNTS_PAGE, payload });
export const setAccountsTotalCountAction = (payload) => ({ type: SET_ACCOUNTS_TOTAL_COUNT, payload });
export const addAccountAction = (payload) => ({ type: ADD_ACCOUNT, payload });
export const updateAccountAction = (payload) => ({ type: UPDATE_ACCOUNT, payload });

