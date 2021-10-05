import { combineReducers } from "redux";
import { accountReducer } from "./accountReducer";

export const reducer = combineReducers({
    account: accountReducer
});