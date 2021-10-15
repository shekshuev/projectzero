package ru.afso.projectzero.models;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "Researches")
public class Research extends BaseModel {

    private Date createdAt;

    private Date startDate;

    private Date endDate;

    private String title;

    private String description;

    private Survey survey;

}
