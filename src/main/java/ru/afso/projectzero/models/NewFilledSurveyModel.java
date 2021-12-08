package ru.afso.projectzero.models;

import ru.afso.projectzero.entities.FilledSurveyEntity;
import ru.afso.projectzero.entities.SurveyEntity;

import java.util.Date;
import java.util.List;

public class NewFilledSurveyModel {

    private Long id;

    private String instanceId;

    private Date beginDate;

    private Date endDate;

    private boolean completed;

    private List<NewFilledQuestionModel> questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<NewFilledQuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<NewFilledQuestionModel> questions) {
        this.questions = questions;
    }

    public FilledSurveyEntity toEntity() {
        SurveyEntity survey = new SurveyEntity();
        survey.setId(id);
        FilledSurveyEntity filledSurvey = new FilledSurveyEntity();
        filledSurvey.setSurvey(survey);
        filledSurvey.setInstanceId(instanceId);
        filledSurvey.setCreatedAt(new Date());
        filledSurvey.setBeginDate(beginDate);
        filledSurvey.setEndDate(endDate);
        filledSurvey.setCompleted(completed);
//        List<FilledQuestionEntity> filledQuestions = questions.stream()
//                .map(NewFilledQuestionModel::toEntity)
//                .collect(Collectors.toList());
//        for (FilledQuestionEntity q: filledQuestions) {
//            q.setFilledSurvey(filledSurvey);
//        }
//        filledSurvey.setFilledQuestions(filledQuestions);
        return filledSurvey;
    }
}
