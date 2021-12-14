package ru.afso.projectzero.entities;

import ru.afso.projectzero.dto.ResearchDTO;
import ru.afso.projectzero.models.BaseModel;
import ru.afso.projectzero.models.ResearchModel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "researches")
public class ResearchEntity implements ModelConvertable {

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

    @OneToMany(mappedBy = "research")
    private List<SurveyEntity> surveys;



    public ResearchEntity() {}

    public ResearchEntity(ResearchDTO researchDTO) {
        beginDate = researchDTO.getBeginDate();
        endDate = researchDTO.getEndDate();
        title = researchDTO.getTitle();
        description = researchDTO.getDescription();
        createdAt = new Date();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<SurveyEntity> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SurveyEntity> surveys) {
        this.surveys = surveys;
    }

    @Override
    public BaseModel toModel() {
        ResearchModel research = new ResearchModel();
        research.setId(id);
        research.setBeginDate(beginDate);
        research.setEndDate(endDate);
        research.setTitle(title);
        research.setDescription(description);
        if (surveys != null) {
            research.setSurveys(surveys.stream().map(SurveyEntity::toModel).collect(Collectors.toList()));
        }
        return research;
    }
}
