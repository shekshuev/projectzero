package ru.afso.projectzero.models;

import ru.afso.projectzero.entities.ResearchEntity;

import java.util.Date;

public class NewResearchModel {

    private Date beginDate;

    private Date endDate;

    private String title;

    private String description;

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

    public ResearchEntity toEntity() {
        ResearchEntity research = new ResearchEntity();
        research.setBeginDate(beginDate);
        research.setEndDate(endDate);
        research.setTitle(title);
        research.setDescription(description);
        research.setCreatedAt(new Date());
        return research;
    }
}
