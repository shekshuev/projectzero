package ru.afso.projectzero.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "filled_questions")
public class FilledQuestionEntity {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "filled_survey_id")
    private FilledSurveyEntity filledSurvey;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private AnswerEntity answer;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date beginDate;

    @Column(nullable = false)
    private Date endDate;

    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FilledSurveyEntity getFilledSurvey() {
        return filledSurvey;
    }

    public void setFilledSurvey(FilledSurveyEntity filledSurvey) {
        this.filledSurvey = filledSurvey;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    public AnswerEntity getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerEntity answer) {
        this.answer = answer;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
