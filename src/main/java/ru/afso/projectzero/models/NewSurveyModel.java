package ru.afso.projectzero.models;

import ru.afso.projectzero.entities.AnswerEntity;
import ru.afso.projectzero.entities.QuestionEntity;
import ru.afso.projectzero.entities.ResearchEntity;
import ru.afso.projectzero.entities.SurveyEntity;

import java.util.Date;
import java.util.List;

public class NewSurveyModel {

    private Date beginDate;

    private Date endDate;

    private String title;

    private String description;

    private List<QuestionEntity> questions;

    private ResearchEntity research;

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public ResearchEntity getResearch() {
        return research;
    }

    public void setResearch(ResearchEntity research) {
        this.research = research;
    }

    public SurveyEntity toEntity() {
        SurveyEntity survey = new SurveyEntity();
        survey.setBeginDate(beginDate);
        survey.setEndDate(endDate);
        survey.setTitle(title);
        survey.setDescription(description);
        if (questions != null) {
            survey.setQuestions(questions);
            for (QuestionEntity question : questions) {
                question.setSurvey(survey);
                if (question.getAnswers() != null) {
                    for (AnswerEntity answer: question.getAnswers()) {
                        answer.setQuestion(question);
                    }
                }
            }
        }
        survey.setResearch(research);
        survey.setCreatedAt(new Date());
        return survey;
    }
}
