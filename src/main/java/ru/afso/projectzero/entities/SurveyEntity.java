package ru.afso.projectzero.entities;

import com.mapbox.geojson.FeatureCollection;
import ru.afso.projectzero.converters.FeatureCollectionConverter;
import ru.afso.projectzero.dto.SurveyDTO;
import ru.afso.projectzero.models.ExtendedSurveyModel;
import ru.afso.projectzero.models.SurveyModel;
import ru.afso.projectzero.models.geojson.FeatureCollectionModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "surveys")
public class SurveyEntity implements ModelConvertable {

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

    public SurveyEntity() {}

    public SurveyEntity(SurveyDTO surveyDTO) {
        beginDate = surveyDTO.getBeginDate();
        endDate = surveyDTO.getEndDate();
        title = surveyDTO.getTitle();
        description = surveyDTO.getDescription();
        createdAt = new Date();
        area = FeatureCollection.fromJson(surveyDTO.getArea().toString());
        questions = surveyDTO.getQuestions().stream()
        		.map(QuestionEntity::fromDTO)
        		.map(question -> {
        			question.setSurvey(this);
        			return question;
        			}).collect(Collectors.toList());
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

    @Override
    public SurveyModel toModel() {
        SurveyModel survey = new SurveyModel();
        survey.setId(id);
        survey.setBeginDate(beginDate);
        survey.setEndDate(endDate);
        survey.setTitle(title);
        survey.setDescription(description);
        if (questions != null) {
            survey.setQuestions(questions.stream().map(QuestionEntity::toModel).collect(Collectors.toList()));
        }
        return survey;
    }

    public ExtendedSurveyModel toExtendedModel() {
        ExtendedSurveyModel surveyModel = new ExtendedSurveyModel();
        surveyModel.setId(id);
        surveyModel.setBeginDate(beginDate);
        surveyModel.setEndDate(endDate);
        surveyModel.setTitle(title);
        surveyModel.setDescription(description);
        if (questions != null) {
            surveyModel.setQuestions(questions.stream().map(QuestionEntity::toModel).collect(Collectors.toList()));
        }
        if (area != null) {
            surveyModel.setArea(FeatureCollectionModel.fromMapBoxFeatureCollection(area));
        }
        return surveyModel;
    }
}
