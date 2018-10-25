package com.techelevator;

import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;

public class MenuCLI {
	
		private JdbcTemplate jdbcTemplate;
		Park p = new Park();
		Campground c = new Campground();
		Site s = new Site ();
		Reservation r = new Reservation();

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
		
		private Menu menu; 
		
		public MenuCLI(Menu menu) { 
			this.menu = menu;
		}
		
		public void run() {  
			
			System.out.println("Welcome to the National Park Campsite Reservation System");
			System.out.println("Please choose an option. //n");
			
			boolean shouldLoop = true;

			while(shouldLoop) {
				
				String choice = (String)menu.getChoiceFromOptions(VIEW_PARKS_OPTIONS);

				if(choice.equals(VIEW_PARKS_OPTION_1)) {
					
					displayInformation("Acadia");
					
					
				} else if(choice.equals(VIEW_PARKS_OPTION_2)) {
					displayInformation("Arches");
				} else if(choice.equals(VIEW_PARKS_OPTION_3)) {
					displayInformation("Cuyahoga National Valley");
				} else if(choice.equals(VIEW_PARKS_OPTION_EXIT)) {
					
				       shouldLoop = false;	  
				}

			}
		}

	/**********************************************************************
	 * main() method invokes main processing method run()
	 **********************************************************************/ 			
		
		public static void main(String[] args) {
			Menu menu = new Menu(System.in, System.out);  // Define Menu object with input and output files
			MenuCLI mainMenu = new MenuCLI(menu);		  // Define a MenuClI object to handle menus
			mainMenu.run();     						  // Process using MenuCLI control logic
		}
		
		public void campgroundMenu(String park) {
			
			boolean shouldLoop = true;

			while(shouldLoop) {
				
				String choice = (String)menu.getChoiceFromOptions(CAMPGROUND_OPTIONS);

				if(choice.equals(CAMPGROUND_OPTION_1)) {
					// process for option 1 choice - good place for a method call
				} else if(choice.equals(CAMPGROUND_OPTION_2)) {
					// process for option 2 choice - good place for a method call
				} else if(choice.equals(VIEW_PARKS_OPTION_EXIT)) {
					// do any end of loop processing
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
		
		public void displayInformation(String park) {
			
			String displayQuery = "Select * FROM park WHERE name = ?";
			SqlRowSet parkChoice = jdbcTemplate.queryForRowSet(displayQuery, park);

			String location = parkChoice.getString("location");
			Date established = parkChoice.getDate("establish_date");
			int area = parkChoice.getInt("area");
			int annualVisitors = parkChoice.getInt("visitors");
			String description = parkChoice.getString("description");
			
			System.out.println("Park Information Screen"
					+ "\\n ***********************"
					+ "\\n" + park + " National Park"   
					+ "\\n Location: \t" + location
					+ "\\n Established: \t" + established
					+ "\\n Area: \t" + area
					+ "\\n Annual Visitors: \t" + annualVisitors
					+ "\\n"
					+ description);
		}

}
