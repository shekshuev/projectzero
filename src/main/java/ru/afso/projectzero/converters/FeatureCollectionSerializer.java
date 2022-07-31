package ru.afso.projectzero.converters;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Polygon;
import com.mapbox.geojson.Point;

public class FeatureCollectionSerializer extends StdSerializer<FeatureCollection> {
	
	public FeatureCollectionSerializer() {
		this(null);
	}
	
	public FeatureCollectionSerializer(Class<FeatureCollection> t) {
		super(t);
	}

	@Override
	public void serialize(FeatureCollection value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeStringField("type", value.type());
		gen.writeFieldName("features");
		gen.writeStartArray();
		for (Feature feature: value.features()) {
			gen.writeStartObject();
			gen.writeStringField("type", feature.type());
			gen.writeFieldName("geometry");
			gen.writeStartObject();
			gen.writeStringField("type", feature.geometry().type());
			if (feature.geometry().type().equals("Polygon")) {
				Polygon polygon = (Polygon)feature.geometry();
				gen.writeFieldName("coordinates");
				gen.writeStartArray();
				for (List<Point> points: polygon.coordinates()) {
					gen.writeStartArray();
					for (Point point: points) {
						gen.writeArray(point.coordinates().stream().mapToDouble(d -> d).toArray(), 0, 2);
					}
					gen.writeEndArray();
				}
				gen.writeEndArray();
			}
			gen.writeEndObject();
			gen.writeEndObject();
		}
		gen.writeEndArray();
		gen.writeEndObject();
		
	}

}
