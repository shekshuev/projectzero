package ru.afso.projectzero.dto;

public class FeatureDTO {

    private String type;

    private GeometryDTO geometry;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeometryDTO getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryDTO geometry) {
        this.geometry = geometry;
    }

}
