package ru.afso.projectzero.converters;

import com.google.gson.Gson;
import com.mapbox.geojson.FeatureCollection;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class FeatureCollectionConverter implements AttributeConverter<FeatureCollection, String> {

    @Override
    public String convertToDatabaseColumn(FeatureCollection entityValue) {
        return entityValue.toJson();
    }

    @Override
    public FeatureCollection convertToEntityAttribute(String databaseValue) {
        if (databaseValue != null) {
            String json = databaseValue.replaceAll("^\"|\"$", "");
            json = json.replaceAll("\\\\\"", "\"");
            return FeatureCollection.fromJson(json);
        } else {
            return null;
        }

    }
}