package ru.afso.projectzero.models;

import ru.afso.projectzero.entities.AnswerEntity;
import ru.afso.projectzero.entities.FilledQuestionEntity;
import ru.afso.projectzero.entities.QuestionEntity;

import java.util.Date;

public class NewFilledQuestionModel {

    private Long id;

    private Long answerId;

    private Date createdAt;

    private Date beginDate;

    private Date endDate;

    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FilledQuestionEntity toEntity() {
        FilledQuestionEntity questionEntity = new FilledQuestionEntity();
        QuestionEntity question = new QuestionEntity();
        question.setId(id);
        AnswerEntity answer = new AnswerEntity();
        answer.setId(answerId);
        questionEntity.setQuestion(question);
        questionEntity.setAnswer(answer);
        questionEntity.setCreatedAt(new Date());
        questionEntity.setBeginDate(beginDate);
        questionEntity.setEndDate(endDate);
        questionEntity.setText(text);
        return questionEntity;
    }
}
