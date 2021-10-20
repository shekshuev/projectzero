import { useSelector } from "react-redux";
import { loadSurvey } from "../api/survey";
import { useEffect, useState } from "react";

const useSurvey = (id) => {
    const surveys = useSelector(state => state.survey.surveys);
    const [survey, setSurvey] = useState(surveys.find(s => s.id === id));

    useEffect(() => {
        if (!survey) {
            const getSurvey = async () => {
                const result = await loadSurvey(id);
                if (result.success) {
                    setSurvey(result.payload);
                }
            };
            getSurvey();
        }
    }, [survey])
    return [survey, setSurvey];
}

export default useSurvey;