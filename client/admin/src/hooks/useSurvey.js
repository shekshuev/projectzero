import { useSelector } from "react-redux";
import { loadSurvey } from "../api/survey";
import { useEffect, useState } from "react";

const useSurvey = (id) => {
    const surveys = useSelector(state => state.survey.surveys);
    const [survey, setSurvey] = useState(surveys.find(s => s.id === id));

    useEffect(() => {
        if (!survey) {
            if (id) {
                const getSurvey = async () => {
                    const result = await loadSurvey(id);
                    if (result.success) {

                        setSurvey({...result.payload, questions: result.payload?.questions ?? []});
                    }
                };
                getSurvey();
            } else {
                setSurvey({
                    createdAt: null,
                    beginDate: null,
                    endDate: null,
                    title: "",
                    description: "",
                    questions: [],
                    research: null
                })
            }
        }
    }, [])
    return [survey, setSurvey];
}

export default useSurvey;