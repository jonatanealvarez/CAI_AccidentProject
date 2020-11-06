package com.example.demo.dto;

import com.example.demo.model.ModeReason;

public class CommonReason {

    private ModeReason temperature_F;
    private ModeReason visibility_MI;
    private ModeReason humidity_AVG;
    private ModeReason wind_Direction;
    private ModeReason pressure_IN;
    private ModeReason wind_Speed_MPH;

    public ModeReason getTemperature_F() {
        return temperature_F;
    }

    public void setTemperature_F(ModeReason temperature_F) {
        this.temperature_F = temperature_F;
    }


    public ModeReason getVisibility_MI() {
        return visibility_MI;
    }

    public void setVisibility_MI(ModeReason visibility_MI) {
        this.visibility_MI = visibility_MI;
    }

    public ModeReason getHumidity_AVG() {
        return humidity_AVG;
    }

    public void setHumidity_AVG(ModeReason humidity_AVG) {
        this.humidity_AVG = humidity_AVG;
    }

    public ModeReason getWind_Direction() {
        return wind_Direction;
    }

    public void setWind_Direction(ModeReason wind_Direction) {
        this.wind_Direction = wind_Direction;
    }

    public ModeReason getPressure_IN() {
        return pressure_IN;
    }

    public void setPressure_IN(ModeReason pressure_IN) {
        this.pressure_IN = pressure_IN;
    }

    public ModeReason getWind_Speed_MPH() {
        return wind_Speed_MPH;
    }

    public void setWind_Speed_MPH(ModeReason wind_Speed_MPH) {
        this.wind_Speed_MPH = wind_Speed_MPH;
    }
}
