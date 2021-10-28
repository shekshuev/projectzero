import React, { useState } from 'react';
import { Grid, Typography, Button, Paper } from "@mui/material";
import { Add } from "@mui/icons-material";
import { useTranslation } from "react-i18next";
import Question from "./Question";

const Questions = ({questionAdded, questionChanged, questionDeleted, questions, loading}) => {

    const { t } = useTranslation();

    const [selectedQuestion, setSelectedQuestion] = useState();

    const addQuestion = () => {
        questionAdded({
            id: Date.now(),
            title: "",
            type: "",
            answers: []
        })
    };
    return (
        <React.Fragment>
            <Typography sx={{my:2}}
                        variant="h4"
                        component="div">{ t("survey:questions:header") }</Typography>
            <Grid container
                  spacing={2}
                  direction="column">
                {
                    questions?.length > 0
                        ?
                        questions.map((question, i) => (
                            <Question key={i}
                                      question={question}
                                      loading={loading}
                                      questionChanged={questionChanged}
                                      questionDeleted={questionDeleted}/>
                        ))
                        :
                        <Grid item>
                            <Typography sx={{py:3}} variant="button"
                                        align="center"
                                        component={Paper}>{ t("survey:questions:empty") }</Typography>
                        </Grid>
                }


                <Grid sx={{py:2}}
                      item
                      container
                      justifyContent="center">
                    <Button variant="text"
                            color="primary"
                            onClick={addQuestion}
                            disabled={loading}>{ t("survey:questions:addQuestionButton") }</Button>
                </Grid>


            </Grid>
        </React.Fragment>

    );
};

export default Questions;