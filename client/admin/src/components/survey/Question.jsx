import React, { useState } from 'react';
import { Grid, Paper, TextField, Select, FormControl, FormGroup, FormControlLabel, Switch,
    InputLabel, MenuItem, Button, IconButton, Divider } from "@mui/material";
import { Delete, Close, ContentCopy } from "@mui/icons-material";
import { useTranslation } from "react-i18next";


const Question = ({question, loading, questionChanged, questionDeleted}) => {
    const { t } = useTranslation();

    const [ types ] = useState([
        {
            type: "single",
            text: t("survey:questions:question:types:single")
        },
        {
            type: "multiple",
            text: t("survey:questions:question:types:multiple")
        },
        {
            type: "open",
            text: t("survey:questions:question:types:open")
        }
    ]);

    const addAnswer = () => {
        let answer = {
            id: Date.now(),
            text: "",
            code: "",
            typedText: ""
        };
        questionChanged({...question, answers: [...question.answers, answer]})
    }

    const changeAnswer = (answer) => {
        const answers = question?.answers?.map(a => {
            if (a.id === answer.id) {
                return answer;
            } else {
                return a;
            }
        });
        questionChanged({...question, answers: answers});
    }

    const deleteAnswer = (answer) => {
        questionChanged({...question, answers: question?.answers?.filter(a => a.id !== answer.id)});
    }

    const deleteQuestion = () => {
        questionDeleted(question);
    }

    return (
        <Grid item sx={{mt:2}}>
            <Paper sx={{px:2, pb:2}}>
                <Grid container direction="row" spacing={2}>
                    <Grid item xs={6}>
                        <TextField multiline
                                   fullWidth
                                   value={question?.title ?? ""}
                                   onChange={e=>questionChanged({...question, title:e.target.value})}
                                   label={t("survey:questions:question:title")}/>
                    </Grid>
                    <Grid item xs={6}>
                        <FormControl fullWidth>
                            <InputLabel>{ t("survey:questions:question:type") }</InputLabel>
                            <Select fullWidth
                                    label={t("survey:questions:question:type")}
                                    value={question?.type ?? ""}
                                    onChange={e=>questionChanged({...question, type:e.target.value})}
                                    disabled={loading}>
                                {
                                    types.map(type => (
                                        <MenuItem value={type.type}
                                                  key={type.type}>{ type.text }</MenuItem>
                                    ))
                                }
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item xs={12}>
                        <Grid container
                              direction="column"
                              spacing={2}>
                            {
                                question.type === "open" ? null :
                                    <React.Fragment>
                                        <Grid item xs={12}>
                                            <Grid container
                                                  direction="row"
                                                  spacing={2}>
                                                {
                                                    question?.answers?.map(answer => (
                                                        <React.Fragment key={answer.id}>
                                                            <Grid item xs={6}>
                                                                <TextField variant="standard"
                                                                           value={answer.text}
                                                                           onChange={e=>changeAnswer({...answer, text:e.target.value})}
                                                                           fullWidth
                                                                           label={t("survey:questions:question:answer:answer")}/>
                                                            </Grid>
                                                            <Grid item xs={5}>
                                                                <TextField variant="standard"
                                                                           value={answer.code}
                                                                           onChange={e=>changeAnswer({...answer, code:e.target.value})}
                                                                           fullWidth
                                                                           label={t("survey:questions:question:answer:code")}/>
                                                            </Grid>
                                                            <Grid item xs={1}
                                                                  container
                                                                  justifyContent="flex-end"
                                                                  alignItems="flex-end">
                                                                <IconButton onClick={()=>deleteAnswer(answer)}>
                                                                    <Close />
                                                                </IconButton>
                                                            </Grid>
                                                        </React.Fragment>
                                                    ))
                                                }
                                            </Grid>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <Button variant="text"
                                                    onClick={addAnswer}
                                                    disabled={loading}>{t("survey:questions:question:addAnswer")}</Button>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <Divider />
                                        </Grid>
                                    </React.Fragment>
                            }
                            <Grid item xs={12}
                                  container
                                  direction="row"
                                  justifyContent="flex-end"
                                  spacing={2}>
                                <Grid item>
                                    <IconButton>
                                        <ContentCopy />
                                    </IconButton>
                                </Grid>
                                <Grid item>
                                    <IconButton onClick={deleteQuestion}>
                                        <Delete />
                                    </IconButton>
                                </Grid>
                                <Grid item>
                                    <Divider orientation="vertical" />
                                </Grid>
                                <Grid item>
                                    <FormGroup>
                                        <FormControlLabel control={<Switch checked={question?.required}
                                                                           onChange={e=>questionChanged({...question, required:e.target.checked})}/>}
                                                          labelPlacement="start"
                                                          label={t("survey:questions:question:required")} />
                                    </FormGroup>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </Paper>
        </Grid>
    );
};

export default Question;