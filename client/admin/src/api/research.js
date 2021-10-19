import axios from "axios";
import { API_URL } from "./base";

import { setResearchesAction,
    setResearchesLoadingAction,
    setResearchesErrorAction,
    addResearchAction,
    updateResearchAction,
    setResearchesTotalCountAction } from "../store/reducers/researchReducer";

const url = `${API_URL()}research`;

export const loadResearches = (count, offset) => {
    return async dispatch => {
        dispatch(setResearchesErrorAction(""));
        dispatch(setResearchesLoadingAction(true));
        try {
            let result = await axios.get(url, {
                params: { count, offset }
            });
            if (result.data.success) {
                dispatch(setResearchesTotalCountAction(result.data.payload.total));
                dispatch(setResearchesAction(result.data.payload.researches));
            } else {
                dispatch(setResearchesErrorAction(result.data.payload.message));
                dispatch(setResearchesAction([]));
            }
        } catch (e) {
            dispatch(setResearchesErrorAction(e.message));
            dispatch(setResearchesAction([]));
        } finally {
            dispatch(setResearchesLoadingAction(false));
        }
    };
};

export const loadResearch = async (id) => {
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

export const createResearch = (research) => {
    return async dispatch => {
        dispatch(setResearchesErrorAction(""));
        dispatch(setResearchesLoadingAction(true));
        try {
            console.log(JSON.stringify(research))
            const result = await axios.post(url, JSON.stringify(research), {
                headers: {
                    'Accept': 'application/json',
                    "Content-Type": "application/json"
                }
            });
            if (result.data.success) {
                dispatch(addResearchAction(result.data.payload));
            } else {
                dispatch(setResearchesErrorAction(result.data.payload));
            }
        } catch (e) {
            dispatch(setResearchesErrorAction(e.message));
        } finally {
            dispatch(setResearchesLoadingAction(false));
        }
    };
}

export const updateResearch = (research) => {
    return async dispatch => {
        dispatch(setResearchesErrorAction(""));
        dispatch(setResearchesLoadingAction(true));
        try {
            const result = await axios.put(`${url}/${research.id}`, JSON.stringify(research), {
                headers: {
                    'Accept': 'application/json',
                    "Content-Type": "application/json"
                }
            });
            if (result.data.success) {
                dispatch(updateResearchAction(result.data.payload));
            } else {
                dispatch(setResearchesErrorAction(result.data.payload));
            }
        } catch (e) {
            dispatch(setResearchesErrorAction(e.message));
        } finally {
            dispatch(setResearchesLoadingAction(false));
        }
    };
}

export const removeResearch = (research, count, offset) => {
    return async dispatch => {
        dispatch(setResearchesErrorAction(""));
        dispatch(setResearchesLoadingAction(true));
        try {
            const result = await axios.delete(`${url}/${research.id}`);
            if (result.data.success) {
                dispatch(loadResearches(count, offset));
            } else {
                dispatch(setResearchesErrorAction(result.data.payload));
            }
        } catch (e) {
            dispatch(setResearchesErrorAction(e.message));
        } finally {
            dispatch(setResearchesLoadingAction(false));
        }
    };
}