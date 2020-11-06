package com.example.demo.dto;

import org.springframework.data.annotation.Id;

public class AggregateAccidentCombined {

    @Id
    private CommonReasonCombined temperature;
    private Double count;

    public CommonReasonCombined getTemperature() {
        return temperature;
    }

    public void setTemperature(CommonReasonCombined temperature) {
        this.temperature = temperature;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }
}
