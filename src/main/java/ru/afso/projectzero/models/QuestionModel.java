package ru.afso.projectzero.models;

import java.util.List;

public class QuestionModel extends BaseModel{

    private String title;

    private String type;

    private List<? extends BaseModel> answers;

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

    public List<? extends BaseModel> getAnswers() {
        return answers;
    }

    public void setAnswers(List<? extends BaseModel> answers) {
        this.answers = answers;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
