package com.techelevator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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
	Site s = new Site();
	Reservation r = new Reservation();
	Menu menu = new Menu(System.in, System.out);
	Scanner userInput = new Scanner(System.in);
	private CampgroundDAO campgroundDAO;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;
	private ParkDAO parkDAO;

	private static final String VIEW_PARKS_OPTION_1 = "Acadia";
	private static final String VIEW_PARKS_OPTION_2 = "Arches";
	private static final String VIEW_PARKS_OPTION_3 = "Cuyahoga National Valley Park";
	private static final String VIEW_PARKS_OPTION_EXIT = "Quit";
	private static final String[] VIEW_PARKS_OPTIONS = { VIEW_PARKS_OPTION_1, VIEW_PARKS_OPTION_2, VIEW_PARKS_OPTION_3,
			VIEW_PARKS_OPTION_EXIT };

	private static final String CAMPGROUND_OPTION_1 = "View Campgrounds";
	private static final String CAMPGROUND_OPTION_2 = "Search for Reservation";
	private static final String CAMPGROUND_OPTION_EXIT = "Return to Previous Screen";
	private static final String[] CAMPGROUND_OPTIONS = { CAMPGROUND_OPTION_1, CAMPGROUND_OPTION_2,
			CAMPGROUND_OPTION_EXIT };

	private static final String RESERVATION_OPTION_1 = "Search for Available Reservation";
	private static final String RESERVATION_OPTION_EXIT = "Return to Previous Screen";
	private static final String[] RESERVATION_OPTIONS = { RESERVATION_OPTION_1, RESERVATION_OPTION_EXIT };

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
		campgroundDAO = new JDBCCampgroundDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
		siteDAO = new JDBCSiteDAO(datasource);
		parkDAO = new JDBCParkDAO(datasource);
	}

	public void run() {

		System.out.println("Welcome to the National Park Campsite Reservation System");
		System.out.println("Please choose an option. \n");

		boolean shouldLoop = true;

		while (shouldLoop) {

			String choice = (String) menu.getChoiceFromOptions(VIEW_PARKS_OPTIONS);
			

			if (choice.equals(VIEW_PARKS_OPTION_1)) {
				displayParkInformation("Acadia");
				campgroundMenu("Acadia");
			} else if (choice.equals(VIEW_PARKS_OPTION_2)) {
				displayParkInformation("Arches");
				campgroundMenu("Arches");
			} else if (choice.equals(VIEW_PARKS_OPTION_3)) {
				displayParkInformation("Cuyahoga Valley");
				campgroundMenu("Cuyahoga Valley");
			} else if (choice.equals(VIEW_PARKS_OPTION_EXIT)) {
				System.out.println("Thank you and have a great day!");
				shouldLoop = false;
			}
		}
	}

	public void campgroundMenu(String park) {

		boolean shouldLoop = true;

		while (shouldLoop) {

			String choice = (String) menu.getChoiceFromOptions(CAMPGROUND_OPTIONS);

			if (choice.equals(CAMPGROUND_OPTION_1)) {
				displayCampgroundInformation(park);

			} else if (choice.equals(CAMPGROUND_OPTION_2)) {
				displayCampgroundInformation(park);

			} else if (choice.equals(CAMPGROUND_OPTION_EXIT)) {
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
		
		System.out.println("PARK INFORMATION SCREEN");
		System.out.println(park + "National Park");
		
		String a = String.format("%-18.18s%-15.15s", "Location: ", location);
		String b = String.format("%-18.18s%-15.15s", "Established: ", established);
		String c = String.format("%-18.18s%-15.15s", "Area: ", area + "sq. km");
		String d = String.format("%-18.18s%-15.15s", "Annual Visitors: ", annualVisitors);
		
		System.out.println(a + "\n" + b + "\n" + c + "\n" + d + "\n" + description);
	}

	public void displayCampgroundInformation(String park) {
		System.out.println("\n" + "PARK CAMPGROUNDS" + "\n" + park + " National Park Campgrounds \n");
		List<String> str = new ArrayList<String>();

		String campQuery = "SELECT * FROM campground JOIN park ON park.park_id = campground.park_id WHERE park.name = ?";
		SqlRowSet campChoice = jdbcTemplate.queryForRowSet(campQuery, park);

		String campMenu = String.format("%-15.15s%-15.15s%-15.15s%-15.15s", "CAMP NAME", "OPEN FROM", "OPEN TO",
				"COST PER DAY");
		System.out.println(campMenu);

		while (campChoice.next()) {
			String toArray = String.format("%-18.18s%-15.15s%-15.15s%-15.15s\n", campChoice.getString("name"),
					campChoice.getString("open_from_mm"), campChoice.getString("open_to_mm"),
					campChoice.getBigDecimal("daily_fee"));
			str.add(toArray);
		}
			int y = 0;
			
			for (String z : str) {
				y++;
				System.out.println(y + ") " + z);
		}
			BigDecimal cost;
			String camp;
			boolean shouldLoop = true;
			campChoice.first();
			
			while (shouldLoop) {

				String choice = (String) menu.getChoiceFromOptions(RESERVATION_OPTIONS);

				if (choice.equals(RESERVATION_OPTION_1)) {
					System.out.println("Which campground would you like?");
					String campInput = userInput.nextLine();
					int x = Integer.parseInt(campInput);
					
					if (x == 1) {
						cost = campChoice.getBigDecimal("daily_fee");
						camp = campChoice.getString("name");
					}
					else if (x == 2) {
						campChoice.next();
						cost = campChoice.getBigDecimal("daily_fee");
						camp = campChoice.getString("name");
					}
					else {
						campChoice.next();
						campChoice.next();
						cost = campChoice.getBigDecimal("daily_fee");
						camp = campChoice.getString("name");
					}
					
					System.out.println("When will you be arriving (YYYY-MM-DD)?");
					String dateArrival = userInput.nextLine();
					System.out.println("When will you be leaving (YYYY-MM-DD)?");
					String dateDepart = userInput.nextLine();

					reservationDAO.searchReservation(dateArrival, dateDepart, cost, camp);

				} else if (choice.equals(RESERVATION_OPTION_EXIT)) {
					shouldLoop = false;
				}
			}
	}
}