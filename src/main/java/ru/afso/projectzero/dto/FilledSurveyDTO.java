package ru.afso.projectzero.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class FilledSurveyDTO {

    private Long id;

    @NotBlank(message = "InstanceId is required!")
    private String instanceId;

    @NotNull(message = "Latitude is required!")
    private Double latitude;

    @NotNull(message = "Longitude is required!")
    private Double longitude;

    @NotNull(message = "Begin date is required!")
    private Date beginDate;

    @NotNull(message = "End date is required!")
    private Date endDate;

    @NotNull(message = "Completed flag is required")
    private Boolean completed;

    private List<FilledQuestionDTO> questions;



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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public List<FilledQuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<FilledQuestionDTO> questions) {
        this.questions = questions;
    }

}
