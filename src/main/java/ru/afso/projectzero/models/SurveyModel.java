package ru.afso.projectzero.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import java.util.Date;
import java.util.List;


public class SurveyModel extends BaseModel {

    private Date createdAt;

    private Date beginDate;

    private Date endDate;

    private String title;

    private String description;

    private List<QuestionModel> questions;

    @DBRef
    private ResearchModel research;

    // Change to geojson or something else
    private Object position;

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

    public List<QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }

    public ResearchModel getResearch() {
        return research;
    }

    public void setResearch(ResearchModel research) {
        this.research = research;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }
}
