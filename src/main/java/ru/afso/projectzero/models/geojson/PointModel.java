package ru.afso.projectzero.models.geojson;

import com.mapbox.geojson.Point;

import java.util.List;

public class PointModel implements GeometryModel {

    private String type;

    private List<Double> coordinates;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }



    public static PointModel fromMapBoxPoint(Point point) {
        PointModel newPoint = new PointModel();
        newPoint.setType("Point");
        newPoint.setCoordinates(point.coordinates());
        return newPoint;
    }
}
