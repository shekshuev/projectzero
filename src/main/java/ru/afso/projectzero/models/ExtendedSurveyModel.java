package ru.afso.projectzero.models;

import ru.afso.projectzero.models.geojson.FeatureCollectionModel;

public class ExtendedSurveyModel extends SurveyModel {

    private FeatureCollectionModel area;

    public FeatureCollectionModel getArea() {
        return area;
    }

    public void setArea(FeatureCollectionModel area) {
        this.area = area;
    }

}
