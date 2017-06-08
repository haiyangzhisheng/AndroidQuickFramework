package com.aaagame.proframework.areachoice.entity;

import java.util.List;

public class CityModel {

	private int area_id;
	public String area_name;
	private List<DistrictModel> district;

	public CityModel(int area_id, String area_name) {
		super();
		this.area_id = area_id;
		this.area_name = area_name;
	}

	public CityModel(int area_id, String area_name, List<DistrictModel> district) {
		super();
		this.area_id = area_id;
		this.area_name = area_name;
		this.district = district;
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

	public List<DistrictModel> getDistrict() {
		return district;
	}

	public void setDistrict(List<DistrictModel> district) {
		this.district = district;
	}

}
