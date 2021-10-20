import { combineReducers } from "redux";
import { accountReducer } from "./accountReducer";
import { researchReducer } from "./researchReducer";
import { surveyReducer } from "./surveyReducer";

export const reducer = combineReducers({
    account: accountReducer,
    research: researchReducer,
    survey: surveyReducer
});