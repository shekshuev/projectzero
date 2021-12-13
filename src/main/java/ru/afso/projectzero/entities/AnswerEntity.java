package ru.afso.projectzero.entities;

import ru.afso.projectzero.models.AnswerModel;
import ru.afso.projectzero.models.BaseModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "answers")
public class AnswerEntity implements ModelConvertable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String text;

    private int code;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    public List<FilledQuestionEntity> filledQuestions;

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

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    @Override
    public BaseModel toModel() {
        AnswerModel answer = new AnswerModel();
        answer.setId(id);
        answer.setText(text);
        answer.setCode(code);
        return answer;
    }
}
