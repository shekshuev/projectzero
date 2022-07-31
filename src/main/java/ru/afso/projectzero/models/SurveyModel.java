package ru.afso.projectzero.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mapbox.geojson.FeatureCollection;

import ru.afso.projectzero.converters.FeatureCollectionSerializer;


public class SurveyModel extends BaseModel {

    private Date beginDate;

    private Date endDate;

    private String title;

    private String description;
    
    @JsonSerialize(using = FeatureCollectionSerializer.class)
    private FeatureCollection area;

    private List<? extends BaseModel> questions;



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
    
    public FeatureCollection getArea() {
        return area;
    }

    public void setArea(FeatureCollection area) {
        this.area = area;
    }

    public List<? extends BaseModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<? extends BaseModel> questions) {
        this.questions = questions;
    }

}
