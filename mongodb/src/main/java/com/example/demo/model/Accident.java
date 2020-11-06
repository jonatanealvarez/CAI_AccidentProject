package com.example.demo.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collection;


@Document
public class Accident {

	@Id
	public String id;
	@Field("start_location")
	private Point startLocation;
	@Field("end_location")
	private Point endLocation;
	@Field("Side")
	public String side;
	@Field("Street")
	public String street;
	@Field("County")
	public String county;
	@Field("Source")
	public String source;
	@Field("Wind_Chill(F)")
	public String wind_Chill;
	@Field("Crossing")
	public String crossing;
	@Field("Nautical_Twilight")
	public String nautical_Twilight;
	@Field("Start_Lat")
	public String start_Lat;
	@Field("Station")
	public String station;
	@Field("Turning_Loop")
	public String turning_Loop;
	@Field("Precipitation(in)")
	public String precipitation;
	@Field("Traffic_Signal")
	public String Traffic_Signal;
	@Field("Civil_Twilight")
	public String Civil_Twilight;
	@Field("Wind_Direction")
	public String Wind_Direction;
	@Field("Distance(mi)")
	public String distance;
	@Field("Country")
	public String country;
	@Field("End_Time")
	public String end_Time;
	@Field("Stop")
	public String stop;
	@Field("Start_Time")
	public String start_time;
	@Field("Temperature(F)")
	public String temperature;
	@Field("Visibility(mi)")
	public String visibility;
	@Field("Humidity(%)")
	public String humidity;
	@Field("Description")
	public String description;
	@Field("@timestamp")
	public String timestamp;

	public Point getStartLocation() {
		return startLocation;
	}

	public String getDescription() {
		return description;
	}

	public void setStartLocation(Point startLocation) {
		this.startLocation = startLocation;
	}

	public String getStreet() {
		return street;
	}

	public String getCounty() {
		return county;
	}

	public String getSource() {
		return source;
	}

	public String getWind_Chill() {
		return wind_Chill;
	}

	public String getCrossing() {
		return crossing;
	}

	public String getNautical_Twilight() {
		return nautical_Twilight;
	}

	public String getStart_Lat() {
		return start_Lat;
	}

	public String getStation() {
		return station;
	}

	public String getTurning_Loop() {
		return turning_Loop;
	}

	public String getPrecipitation() {
		return precipitation;
	}

	public String getTraffic_Signal() {
		return Traffic_Signal;
	}

	public String getCivil_Twilight() {
		return Civil_Twilight;
	}

	public String getWind_Direction() {
		return Wind_Direction;
	}

	public String getDistance() {
		return distance;
	}

	public String getCountry() {
		return country;
	}

	public String getEnd_Time() {
		return end_Time;
	}

	public Point getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(Point endLocation) {
		this.endLocation = endLocation;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String aStop) {
		this.stop = aStop;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String aStart_time) {
		this.start_time = aStart_time;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String aSide) {
		this.side = aSide;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String anId) {
		this.id = anId;
	}

}
