package com.aaagame.proframework.areachoice.entity;

import java.util.List;

public class ProvinceModel {
	
	private int area_id;
	private String area_name;
	private List<CityModel> city;

	public ProvinceModel(int area_id, String area_name, List<CityModel> city) {
		super();
		this.area_id = area_id;
		this.area_name = area_name;
		this.city = city;
	}

	public int getArea_id() {
		return area_id;
	}

	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public List<CityModel> getCity() {
		return city;
	}

	public void setCity(List<CityModel> city) {
		this.city = city;
	}

}
