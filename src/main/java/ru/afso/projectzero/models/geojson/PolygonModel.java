package ru.afso.projectzero.models.geojson;

import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PolygonModel implements GeometryModel {

    private String type;

    private List<List<List<Double>>> coordinates;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<List<Double>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }

    public static PolygonModel fromMapBoxPolygon(Polygon polygon) {
        PolygonModel polygonModel = new PolygonModel();
        polygonModel.setType("Polygon");
        polygonModel.coordinates = new ArrayList<>();
        for (List<Point> list: polygon.coordinates()) {
            polygonModel.coordinates.add(list.stream().map(Point::coordinates).collect(Collectors.toList()));
        }
        return polygonModel;
    }

}
