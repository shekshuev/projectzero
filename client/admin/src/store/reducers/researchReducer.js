const initialState = {
    error: "",
    loading: false,
    researches: [],
    count: 5,
    page: 0,
    totalCount: 0
}

const SET_RESEARCHES = "SET_RESEARCHES";
const SET_RESEARCHES_LOADING = "SET_RESEARCHES_LOADING";
const SET_RESEARCHES_ERROR = "SET_RESEARCHES_ERROR";
const SET_RESEARCHES_COUNT = "SET_RESEARCHES_COUNT";
const SET_RESEARCHES_PAGE = "SET_RESEARCHES_PAGE";
const SET_RESEARCHES_TOTAL_COUNT = "SET_RESEARCHES_TOTAL_COUNT";
const ADD_RESEARCH = "ADD_RESEARCH";
const UPDATE_RESEARCH = "UPDATE_RESEARCH";

export const researchReducer = (state = initialState, action) => {
    switch (action.type){
        case SET_RESEARCHES:
            return { ...state, researches: action.payload };
        case SET_RESEARCHES_LOADING:
            return { ...state, loading: action.payload };
        case SET_RESEARCHES_ERROR:
            return { ...state, error: action.payload };
        case SET_RESEARCHES_COUNT:
            return { ...state, count: action.payload };
        case SET_RESEARCHES_PAGE:
            return { ...state, page: action.payload };
        case SET_RESEARCHES_TOTAL_COUNT:
            return { ...state, totalCount: action.payload};
        case ADD_RESEARCH:
            return { ...state, researches: [...state.researches, action.payload] };
        case UPDATE_RESEARCH:
            return { ...state, researches: state.researches.map(research => {
                    if (research.id === action.payload.id) {
                        return action.payload;
                    } else {
                        return research;
                    }
                })};
        default:
            return state;
    }
}

export const setResearchesAction = (payload) => ({ type: SET_RESEARCHES, payload });
export const setResearchesLoadingAction = (payload) => ({ type: SET_RESEARCHES_LOADING, payload });
export const setResearchesErrorAction = (payload) => ({ type: SET_RESEARCHES_ERROR, payload });
export const setResearchesCountAction = (payload) => ({ type: SET_RESEARCHES_COUNT, payload });
export const setResearchesPageAction = (payload) => ({ type: SET_RESEARCHES_PAGE, payload });
export const setResearchesTotalCountAction = (payload) => ({ type: SET_RESEARCHES_TOTAL_COUNT, payload });
export const addResearchAction = (payload) => ({ type: ADD_RESEARCH, payload });
export const updateResearchAction = (payload) => ({ type: UPDATE_RESEARCH, payload });