package ru.afso.projectzero.dto;

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

    @NotNull(message = "Research id is required")
    private long researchId;

    @NotNull(message = "Area is required")
    private FeatureCollectionDTO area;
    
    private List<QuestionDTO> questions;



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

    public long getResearchId() {
        return researchId;
    }

    public void setResearchId(long researchId) {
        this.researchId = researchId;
    }

    public FeatureCollectionDTO getArea() {
        return area;
    }

    public void setArea(FeatureCollectionDTO area) {
        this.area = area;
    }

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

}
