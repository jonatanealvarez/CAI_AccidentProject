package com.example.demo.dto;

import com.example.demo.model.Accident;

public class AccidentDateBetween {

	String id;
	String start_time;
	String temperature;
	String country;
	String county;
	String street;
	String description;

	public AccidentDateBetween(Accident anAccident) {
		this.setId(anAccident.getId());
		this.setStart_time(anAccident.getStart_time());
		this.setTemperature(anAccident.getTemperature());
		this.setCountry(anAccident.getCountry());
		this.setCounty(anAccident.getCounty());
		this.setStreet(anAccident.getStreet());
		this.setDescription(anAccident.getDescription());
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCounty() {
		return county;
	}

	public String getStreet() {
		return street;
	}

	public String getDescription() {
		return description;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}


	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String anId) {
		this.id = anId;
	}
}
