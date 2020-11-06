package com.example.demo.dto;

import org.springframework.data.mongodb.core.mapping.Field;

public class CommonReasonCombined {

    @Field("Temperature(F)")
    public String temperature;
    @Field("Visibility(mi)")
    public String visibility;
    @Field("Humidity(%)")
    public String humidity;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
