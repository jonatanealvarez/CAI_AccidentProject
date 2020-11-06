package elasticsearch.accident.dto;

import elasticsearch.accident.model.Accident;
import org.springframework.data.geo.Point;

public class AccidentInRadius {
    String id;
    Point start_location;
    String country;
    String county;
    String street;
    String description;

    public AccidentInRadius(Accident anAccident) {
        this.setId(anAccident.getId());
        this.setStart_location(new Point(anAccident.getStartLocation().getX(), anAccident.getStartLocation().getY()));
        this.setCountry(anAccident.getCountry());
        this.setCounty(anAccident.getCounty());
        this.setStreet(anAccident.getStreet());
        this.setDescription(anAccident.getDescription());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Point getStart_location() {
        return start_location;
    }

    public void setStart_location(Point start_location) {
        this.start_location = start_location;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
