package com.techelevator.model;

import java.math.BigDecimal;

public class Campground {
	
	int campgroundId;
	int parkId;
	String name;
	String openFromMonth;
	String openToMoneth;
	BigDecimal dailyFee;
	
	public Campground() {
				
	}

	public int getCampgroundId() {
		return campgroundId;
	}

	public int getParkId() {
		return parkId;
	}

	public String getName() {
		return name;
	}

	public String getOpenFromMonth() {
		return openFromMonth;
	}

	public String getOpenToMoneth() {
		return openToMoneth;
	}

	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	
	

}
