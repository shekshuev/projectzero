package ru.afso.projectzero.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "surveys")
public class Survey {

    @Id
    private String id;

    private Date createdAt;

    private Date startDate;

    private Date endDate;

    private String title;

    private String description;

}
