package ru.afso.projectzero.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "questionaries")
public class Questionary {

    @Id
    private String id;

    private Date createdAt;

    @DBRef
    private Survey survey;

    private List<Question> questions;

    public Date getCreatedAt() {
        return createdAt;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

}
