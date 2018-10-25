package com.techelevator;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.ParkDAO;
import com.techelevator.model.ReservationDAO;
import com.techelevator.model.SiteDAO;
import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;

public class CampgroundCLI {

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(BasicDataSource datasource) {
		CampgroundDAO campgroundDAO = new JDBCCampgroundDAO(datasource);
		ReservationDAO reservationDAO = new JDBCReservationDAO(datasource);
		SiteDAO siteDAO = new JDBCSiteDAO(datasource);
		ParkDAO parkDAO = new JDBCParkDAO(datasource);
	}
	
	public void run() {
		
	}
	
}
