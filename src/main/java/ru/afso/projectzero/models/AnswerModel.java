package ru.afso.projectzero.models;

public class AnswerModel extends BaseModel {

    private String text;

    private int code;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
