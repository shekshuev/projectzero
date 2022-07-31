package ru.afso.projectzero.entities;

import com.mapbox.geojson.FeatureCollection;
import ru.afso.projectzero.converters.FeatureCollectionConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "surveys")
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date createdAt;

    private Date beginDate;

    private Date endDate;

    @Column(nullable = false, unique = true)
    private String title;

    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<FilledSurveyEntity> filledSurveys;

    @Convert(converter = FeatureCollectionConverter.class)
    @Column(columnDefinition = "json")
    private FeatureCollection area;

    public SurveyEntity() {
    	createdAt = new Date();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public FeatureCollection getArea() {
        return area;
    }

    public void setArea(FeatureCollection area) {
        this.area = area;
    }

    public void addQuestion(QuestionEntity question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(question);
        if (question.getAnswers() != null) {
            for (AnswerEntity answer: question.getAnswers()) {
                answer.setQuestion(question);
            }
        }
    }

}
