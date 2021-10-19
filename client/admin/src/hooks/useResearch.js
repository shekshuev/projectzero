import { useSelector } from "react-redux";
import { loadResearch } from "../api/research";
import { useEffect, useState } from "react";

const useResearch = (id) => {
    const researches = useSelector(state => state.research.researches);
    const [research, setResearch] = useState(researches.find(r => r.id === id));

    useEffect(() => {
        if (!research) {
            const getResearch = async () => {
                const result = await loadResearch(id);
                if (result.success) {
                    setResearch(result.payload);
                }
            };
            getResearch();
        }
    }, [research])
    return [research, setResearch];
}

export default useResearch;