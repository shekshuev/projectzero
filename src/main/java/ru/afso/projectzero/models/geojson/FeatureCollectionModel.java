package ru.afso.projectzero.models.geojson;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;

import java.util.ArrayList;
import java.util.List;

public class FeatureCollectionModel {

    private String type;

    private List<FeatureModel> features;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FeatureModel> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureModel> features) {
        this.features = features;
    }



    public static FeatureCollectionModel fromMapBoxFeatureCollection(FeatureCollection featureCollection) {
        FeatureCollectionModel featureCollectionModel = new FeatureCollectionModel();
        featureCollectionModel.setType("FeatureCollection");
        featureCollectionModel.features = new ArrayList<>();
        if (featureCollection.features() != null) {
            for (Feature feature: featureCollection.features()) {
                featureCollectionModel.features.add(FeatureModel.fromMapBoxFeature(feature));
            }
        }
        return featureCollectionModel;
    }

}
