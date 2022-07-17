package ru.afso.projectzero.entities;

import com.mapbox.geojson.FeatureCollection;
import ru.afso.projectzero.models.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "locations")
public class LocationEntity implements ModelConvertable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private FeatureCollection area;

    private int category;

    

    @Override
    public BaseModel toModel() {
        return null;
    }
}
