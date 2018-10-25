package com.techelevator.model;

public class Site {
	
	int siteId;
	int campgroundId;
	int siteNumber;
	int maxOccupancy;
	boolean accessible;
	int maxRvLength;
	boolean utilities;
	
	public Site() {
		
		
	}

	public int getSiteId() {
		return siteId;
	}

	public int getCampgroundId() {
		return campgroundId;
	}

	public int getSiteNumber() {
		return siteNumber;
	}

	public int getMaxOccupancy() {
		return maxOccupancy;
	}

	public boolean isAccessible() {
		return accessible;
	}

	public int getMaxRvLength() {
		return maxRvLength;
	}

	public boolean isUtilities() {
		return utilities;
	}
	
	

}
