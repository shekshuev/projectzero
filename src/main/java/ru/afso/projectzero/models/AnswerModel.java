package ru.afso.projectzero.models;

public class AnswerModel extends BaseModel {

    private String text;

    private String code;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
