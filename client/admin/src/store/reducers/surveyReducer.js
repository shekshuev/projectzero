const initialState = {
    error: "",
    loading: false,
    surveys: [],
    count: 5,
    page: 0,
    totalCount: 0
}

const SET_SURVEYS = "SET_SURVEYS";
const SET_SURVEYS_LOADING = "SET_SURVEYS_LOADING";
const SET_SURVEYS_ERROR = "SET_SURVEYS_ERROR";
const SET_SURVEYS_COUNT = "SET_SURVEYS_COUNT";
const SET_SURVEYS_PAGE = "SET_SURVEYS_PAGE";
const SET_SURVEYS_TOTAL_COUNT = "SET_SURVEYS_TOTAL_COUNT";
const ADD_SURVEY = "ADD_SURVEY";
const UPDATE_SURVEY = "UPDATE_SURVEY";

export const surveyReducer = (state = initialState, action) => {
    switch (action.type){
        case SET_SURVEYS:
            return { ...state, surveys: action.payload };
        case SET_SURVEYS_LOADING:
            return { ...state, loading: action.payload };
        case SET_SURVEYS_ERROR:
            return { ...state, error: action.payload };
        case SET_SURVEYS_COUNT:
            return { ...state, count: action.payload };
        case SET_SURVEYS_PAGE:
            return { ...state, page: action.payload };
        case SET_SURVEYS_TOTAL_COUNT:
            return { ...state, totalCount: action.payload};
        case ADD_SURVEY:
            return { ...state, surveys: [...state.surveys, action.payload] };
        case UPDATE_SURVEY:
            return { ...state, surveys: state.surveys.map(survey => {
                    if (survey.id === action.payload.id) {
                        return action.payload;
                    } else {
                        return survey;
                    }
                })};
        default:
            return state;
    }
}

export const setSurveysAction = (payload) => ({ type: SET_SURVEYS, payload });
export const setSurveysLoadingAction = (payload) => ({ type: SET_SURVEYS_LOADING, payload });
export const setSurveysErrorAction = (payload) => ({ type: SET_SURVEYS_ERROR, payload });
export const setSurveysCountAction = (payload) => ({ type: SET_SURVEYS_COUNT, payload });
export const setSurveysPageAction = (payload) => ({ type: SET_SURVEYS_PAGE, payload });
export const setSurveysTotalCountAction = (payload) => ({ type: SET_SURVEYS_TOTAL_COUNT, payload });
export const addSurveyAction = (payload) => ({ type: ADD_SURVEY, payload });
export const updateSurveyAction = (payload) => ({ type: UPDATE_SURVEY, payload });