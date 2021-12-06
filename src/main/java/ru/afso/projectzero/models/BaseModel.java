package ru.afso.projectzero.models;

import org.springframework.data.annotation.Id;

public class BaseModel {

    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
