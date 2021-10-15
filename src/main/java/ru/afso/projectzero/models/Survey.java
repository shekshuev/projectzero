package ru.afso.projectzero.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "surveys")
public class Survey extends BaseModel {

    private Date createdAt;

    private Date startDate;

    private Date endDate;

    private List<Question> questions;

    @DBRef
    private Research research;

    // Change to geojson or something else
    private Object position;

}
