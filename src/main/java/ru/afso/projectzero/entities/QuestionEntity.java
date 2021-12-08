package ru.afso.projectzero.entities;

import ru.afso.projectzero.models.AnswerModel;
import ru.afso.projectzero.models.BaseModel;
import ru.afso.projectzero.models.QuestionModel;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "questions")
public class QuestionEntity implements ModelConvertable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String type;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<AnswerEntity> answers;

    private boolean required;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyEntity survey;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    public List<FilledQuestionEntity> filledQuestions;

    public QuestionEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public SurveyEntity getSurvey() {
        return survey;
    }

    public void setSurvey(SurveyEntity survey) {
        this.survey = survey;
    }

    public List<FilledQuestionEntity> getFilledQuestions() {
        return filledQuestions;
    }

    public void setFilledQuestions(List<FilledQuestionEntity> filledQuestions) {
        this.filledQuestions = filledQuestions;
    }

    @Override
    public BaseModel toModel() {
        QuestionModel question = new QuestionModel();
        question.setId(id);
        question.setTitle(title);
        question.setType(type);
        question.setRequired(required);
        if (answers != null) {
            question.setAnswers(answers.stream().map(AnswerEntity::toModel).collect(Collectors.toList()));
        }
        return question;
    }
}
