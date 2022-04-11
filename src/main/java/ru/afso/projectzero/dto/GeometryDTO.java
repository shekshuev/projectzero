package ru.afso.projectzero.dto;

import java.util.ArrayList;

public class GeometryDTO<T> {

    private String type;

    private ArrayList<T> coordinates;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<T> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<T> coordinates) {
        this.coordinates = coordinates;
    }

}
