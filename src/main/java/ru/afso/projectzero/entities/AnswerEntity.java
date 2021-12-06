package ru.afso.projectzero.entities;

import ru.afso.projectzero.models.BaseModel;

public class AnswerEntity extends BaseEntity implements ModelConvertable {

    private String text;

    private int code;

    private boolean selected;

    private String typedText;

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTypedText() {
        return typedText;
    }

    public void setTypedText(String typedText) {
        this.typedText = typedText;
    }

    @Override
    public BaseModel toModel() {
        return null;
    }
}
