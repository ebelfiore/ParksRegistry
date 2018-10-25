package com.techelevator.model;

import java.sql.Date;

public class Park {
	int parkId;
	String name;
	String location;
	Date establishedDate;
	int area;
	int visitors;
	String description;
	
	public Park() {

	}

	public int getParkId() {
		return parkId;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public Date getEstablishedDate() {
		return establishedDate;
	}

	public int getArea() {
		return area;
	}

	public int getVisitors() {
		return visitors;
	}

	public String getDescription() {
		return description;
	}
	
	

}
