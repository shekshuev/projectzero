package ru.afso.projectzero.entities;

import ru.afso.projectzero.models.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class AnswerEntity implements ModelConvertable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String text;

    private int code;

    private boolean selected;

    private String typedText;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    public AnswerEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    @Override
    public BaseModel toModel() {
        return null;
    }
}
