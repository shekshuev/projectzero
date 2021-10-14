package ru.afso.projectzero.models;

import org.springframework.data.annotation.Id;

public class BaseModel {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
