import { combineReducers } from "redux";
import { accountReducer } from "./accountReducer";
import { researchReducer } from "./researchReducer";

export const reducer = combineReducers({
    account: accountReducer,
    research: researchReducer
});