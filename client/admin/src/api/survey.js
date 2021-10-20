import axios from "axios";
import { API_URL } from "./base";

import { setSurveysAction,
    setSurveysLoadingAction,
    setSurveysErrorAction,
    addSurveyAction,
    updateSurveyAction,
    setSurveysTotalCountAction } from "../store/reducers/surveyReducer";

const url = `${API_URL()}survey`;

export const loadSurveys = (count, offset) => {
    return async dispatch => {
        dispatch(setSurveysErrorAction(""));
        dispatch(setSurveysLoadingAction(true));
        try {
            let result = await axios.get(url, {
                params: { count, offset }
            });
            if (result.data.success) {
                dispatch(setSurveysTotalCountAction(result.data.payload.total));
                dispatch(setSurveysAction(result.data.payload.surveys));
            } else {
                dispatch(setSurveysErrorAction(result.data.payload.message));
                dispatch(setSurveysAction([]));
                dispatch(setSurveysTotalCountAction(0));
            }
        } catch (e) {
            dispatch(setSurveysErrorAction(e.message));
            dispatch(setSurveysAction([]));
            dispatch(setSurveysTotalCountAction(0));
        } finally {
            dispatch(setSurveysLoadingAction(false));
        }
    };
};

export const loadSurvey = async (id) => {
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

export const createSurvey = (survey) => {
    return async dispatch => {
        dispatch(setSurveysErrorAction(""));
        dispatch(setSurveysLoadingAction(true));
        try {
            console.log(JSON.stringify(survey))
            const result = await axios.post(url, JSON.stringify(survey), {
                headers: {
                    'Accept': 'application/json',
                    "Content-Type": "application/json"
                }
            });
            if (result.data.success) {
                dispatch(addSurveyAction(result.data.payload));
            } else {
                dispatch(setSurveysErrorAction(result.data.payload));
            }
        } catch (e) {
            dispatch(setSurveysErrorAction(e.message));
        } finally {
            dispatch(setSurveysLoadingAction(false));
        }
    };
}

export const updateSurvey = (survey) => {
    return async dispatch => {
        dispatch(setSurveysErrorAction(""));
        dispatch(setSurveysLoadingAction(true));
        try {
            const result = await axios.put(`${url}/${survey.id}`, JSON.stringify(survey), {
                headers: {
                    'Accept': 'application/json',
                    "Content-Type": "application/json"
                }
            });
            if (result.data.success) {
                dispatch(updateSurveyAction(result.data.payload));
            } else {
                dispatch(setSurveysErrorAction(result.data.payload));
            }
        } catch (e) {
            dispatch(setSurveysErrorAction(e.message));
        } finally {
            dispatch(setSurveysLoadingAction(false));
        }
    };
}

export const removeSurvey = (survey, count, offset) => {
    return async dispatch => {
        dispatch(setSurveysErrorAction(""));
        dispatch(setSurveysLoadingAction(true));
        try {
            const result = await axios.delete(`${url}/${survey.id}`);
            if (result.data.success) {
                dispatch(loadSurveys(count, offset));
            } else {
                dispatch(setSurveysErrorAction(result.data.payload));
            }
        } catch (e) {
            dispatch(setSurveysErrorAction(e.message));
        } finally {
            dispatch(setSurveysLoadingAction(false));
        }
    };
}
