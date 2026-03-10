package lk.ijse.agmscropservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "crops")
public class Crop {

    @Id
    private String id;
    private String cropName;
    private String season;
    private String fieldLocation;

    public Crop() {
    }

    public Crop(String id, String cropName, String season, String fieldLocation) {
        this.id = id;
        this.cropName = cropName;
        this.season = season;
        this.fieldLocation = fieldLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getFieldLocation() {
        return fieldLocation;
    }

    public void setFieldLocation(String fieldLocation) {
        this.fieldLocation = fieldLocation;
    }
}