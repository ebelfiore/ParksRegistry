package com.techelevator;

import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;
import com.techelevator.model.Reservation;
import com.techelevator.model.ReservationDAO;
import com.techelevator.model.Site;
import com.techelevator.model.SiteDAO;
import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;

public class CampgroundCLI {
	
	private JdbcTemplate jdbcTemplate;
	Park p = new Park();
	Campground c = new Campground();
	Site s = new Site ();
	Reservation r = new Reservation();
	Menu menu = new Menu(System.in, System.out); 
	
	private static final String   VIEW_PARKS_OPTION_1    = "Acadia";
	private static final String   VIEW_PARKS_OPTION_2    = "Arches";
	private static final String   VIEW_PARKS_OPTION_3    = "Cuyahoga National Valley Park";
	private static final String   VIEW_PARKS_OPTION_EXIT = "Quit";
	private static final String[] VIEW_PARKS_OPTIONS = { VIEW_PARKS_OPTION_1, VIEW_PARKS_OPTION_2,
			VIEW_PARKS_OPTION_3, VIEW_PARKS_OPTION_EXIT};

	private static final String   CAMPGROUND_OPTION_1    = "View Campgrounds";
	private static final String   CAMPGROUND_OPTION_2    = "Search for Reservation";
	private static final String   CAMPGROUND_OPTION_EXIT = "Return to Previous Screen";
	private static final String[] CAMPGROUND_OPTIONS = {CAMPGROUND_OPTION_1, CAMPGROUND_OPTION_2,
			CAMPGROUND_OPTION_EXIT};
	
	private static final String   RESERVATION_OPTION_1    = "Search for Available Reservation";
	private static final String   RESERVATION_OPTION_EXIT = "Return to Previous Screen";
	private static final String[] RESERVATION_OPTIONS = {RESERVATION_OPTION_1, RESERVATION_OPTION_EXIT};


	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(BasicDataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
		CampgroundDAO campgroundDAO = new JDBCCampgroundDAO(datasource);
		ReservationDAO reservationDAO = new JDBCReservationDAO(datasource);
		SiteDAO siteDAO = new JDBCSiteDAO(datasource);
		ParkDAO parkDAO = new JDBCParkDAO(datasource);
	}
	
	public void run() {
		
		System.out.println("Welcome to the National Park Campsite Reservation System");
		System.out.println("Please choose an option. \n");
		
		boolean shouldLoop = true;

		while(shouldLoop) {
			
			String choice = (String)menu.getChoiceFromOptions(VIEW_PARKS_OPTIONS);

			if(choice.equals(VIEW_PARKS_OPTION_1)) {		
				displayParkInformation("Acadia");
				campgroundMenu("Acadia");
			} else if(choice.equals(VIEW_PARKS_OPTION_2)) {
				displayParkInformation("Arches");
				campgroundMenu("Arches");
			} else if(choice.equals(VIEW_PARKS_OPTION_3)) {
				displayParkInformation("Cuyahoga National Valley");
				campgroundMenu("Cuyahoga National Valley");
			} else if(choice.equals(VIEW_PARKS_OPTION_EXIT)) {
			       shouldLoop = false;	  
			}
		}
	}
	
	public void campgroundMenu(String park) {
		
		System.out.println("PARK CAMPGROUNDS"
				+ "\n" + park + " National Park Campgrounds");
		
		boolean shouldLoop = true;

		while(shouldLoop) {
			
			String choice = (String)menu.getChoiceFromOptions(CAMPGROUND_OPTIONS);

			if(choice.equals(CAMPGROUND_OPTION_1)) {
				
			} else if(choice.equals(CAMPGROUND_OPTION_2)) {
				
			} else if(choice.equals(VIEW_PARKS_OPTION_EXIT)) {
				
			       shouldLoop = false;	  
			}
		}
	}
	
	public void reservationMenu(String campground) {
		boolean shouldLoop = true;

		while(shouldLoop) {
			
			String choice = (String)menu.getChoiceFromOptions(RESERVATION_OPTIONS);

			if(choice.equals(RESERVATION_OPTION_1)) {
				// process for option 1 choice - good place for a method call
			} else if(choice.equals(RESERVATION_OPTION_EXIT)) {
			       shouldLoop = false;	  
			}
		}
	}
	
	public void displayParkInformation(String park) {
		
		String displayQuery = "Select * FROM park WHERE name = ?";
		SqlRowSet parkChoice = jdbcTemplate.queryForRowSet(displayQuery, park);
		parkChoice.next();
		
		String location = parkChoice.getString("location");
		Date established = parkChoice.getDate("establish_date");
		int area = parkChoice.getInt("area");
		int annualVisitors = parkChoice.getInt("visitors");
		String description = parkChoice.getString("description");
		
		System.out.println("PARK INFORMATION SCREEN"
				+ "\n ***********************"
				+ "\n" + park + " National Park"   
				+ "\n Location: \t" + location
				+ "\n Established: \t" + established
				+ "\n Area: \t" + area
				+ "\n Annual Visitors: \t" + annualVisitors
				+ "\n"
				+ description);
	}
	
	public void displayCampgroundInformation(String park) {
		System.out.print("");
		
		
	}
	
}
