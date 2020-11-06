package com.example.demo.model;

import org.springframework.data.annotation.Id;


public class ModeReason {

    @Id
    private String value;

    private Double count;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }
}
