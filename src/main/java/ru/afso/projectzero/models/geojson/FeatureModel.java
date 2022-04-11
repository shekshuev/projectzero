package ru.afso.projectzero.models.geojson;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;

public class FeatureModel {

    private String type;

    private GeometryModel geometry;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeometryModel getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryModel geometry) {
        this.geometry = geometry;
    }



    public static FeatureModel fromMapBoxFeature(Feature feature) {
        FeatureModel featureModel = new FeatureModel();
        featureModel.setType("Feature");
        if (feature.geometry() instanceof Point) {
            featureModel.setGeometry(PointModel.fromMapBoxPoint((Point)feature.geometry()));
        } else if (feature.geometry() instanceof Polygon) {
            featureModel.setGeometry(PolygonModel.fromMapBoxPolygon((Polygon)feature.geometry()));
        }
        return featureModel;
    }
}
