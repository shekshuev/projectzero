package ru.afso.projectzero.entities;

import ru.afso.projectzero.models.BaseModel;
import ru.afso.projectzero.models.FilledSurveyModel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "filled_surveys")
public class FilledSurveyEntity implements ModelConvertable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String instanceId;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date beginDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyEntity survey;

    @OneToMany(mappedBy = "filledSurvey", cascade = CascadeType.ALL)
    private List<FilledQuestionEntity> filledQuestions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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
        FilledSurveyModel filledSurvey = new FilledSurveyModel();
        filledSurvey.setId(id);
        filledSurvey.setInstanceId(instanceId);
        filledSurvey.setBeginDate(beginDate);
        filledSurvey.setEndDate(endDate);
        filledSurvey.setCompleted(completed);
        return filledSurvey;
    }
}
