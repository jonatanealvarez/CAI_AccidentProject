package com.example.demo.dto;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;

public class DangerousPoint {

    @Id
    private String id;
    @Field("start_location")
    private Point startLocation;
    @Field("amount")
    private Integer amount;


    public Point getStartLocation() {
        return startLocation;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setStartLocation(Point startLocation) {
        this.startLocation = startLocation;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
