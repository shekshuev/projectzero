package ru.afso.projectzero.models;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "researches")
public class Research extends BaseModel {

    private Date createdAt;

    private Date beginDate;

    private Date endDate;

    private String title;

    private String description;

    private List<SurveyModel> surveys;

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

    public List<SurveyModel> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SurveyModel> surveys) {
        this.surveys = surveys;
    }
}
