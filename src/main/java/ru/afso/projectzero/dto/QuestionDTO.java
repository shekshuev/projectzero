package ru.afso.projectzero.dto;

import ru.afso.projectzero.entities.AnswerEntity;

import java.util.List;

public class QuestionDTO {

    private String title;

    private String type;

    private List<AnswerEntity> answers;

    private boolean required;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEntity> answers) {
        this.answers = answers;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
