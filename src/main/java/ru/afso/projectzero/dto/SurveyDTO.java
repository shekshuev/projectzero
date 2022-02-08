package ru.afso.projectzero.dto;

import ru.afso.projectzero.entities.QuestionEntity;
import ru.afso.projectzero.entities.ResearchEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

public class SurveyDTO {

    @NotNull(message = "Begin date is required")
    private Date beginDate;

    @NotNull(message = "End date is required")
    private Date endDate;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private List<QuestionEntity> questions;

    @NotNull(message = "Research is required")
    private ResearchEntity research;



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

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public ResearchEntity getResearch() {
        return research;
    }

    public void setResearch(ResearchEntity research) {
        this.research = research;
    }
}
