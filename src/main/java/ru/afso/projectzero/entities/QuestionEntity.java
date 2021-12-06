package ru.afso.projectzero.entities;

import ru.afso.projectzero.models.BaseModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class QuestionEntity implements ModelConvertable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String type;

    @OneToMany(mappedBy = "question")
    private List<AnswerEntity> answers;

    private boolean required;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private SurveyEntity survey;

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

    @Override
    public BaseModel toModel() {
        return null;
    }
}
