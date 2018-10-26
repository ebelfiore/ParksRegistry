package com.techelevator.model.jdbc;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO{

	
	private JdbcTemplate jdbcTemplate;
	Scanner userInput = new Scanner(System.in);
	
	public JDBCReservationDAO(BasicDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public int searchReservation(String camp, String arrive, String depart, BigDecimal price) {
		
		Date arriveDate = new Date();
		Date departDate = new Date();
		BigDecimal tripDays;
		int id =0;
		SqlRowSet parkChoice;
		
		
		
		try {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		arriveDate = formatter.parse(arrive);
		departDate = formatter.parse(depart);
		
		long difference =  (departDate.getTime()-arriveDate.getTime())/86400000;
        tripDays = new BigDecimal(difference);
		
		String makingReservation = "SELECT * FROM site WHERE site.site_id not in ("
				+ "SELECT site.site_id FROM site "
				+ "JOIN reservation on site.site_id = reservation.site_id WHERE (((? >= from_date) AND (? <= to_date)) "
				+ "OR ((? >= from_date) AND (? <= to_date)) OR ((? <= from_date) AND (? >= to_date)))) LIMIT 5";

		parkChoice = jdbcTemplate.queryForRowSet(makingReservation, arriveDate, arriveDate, departDate, departDate, arriveDate, departDate);		
		
		String columns = String.format("%-15.15s%-15.15s%-15.15s%-15.15s%-15.15s%-15.15s", 
				"Site No.", "Max Occup.", "Accesible?", "Max RV Length", "Utility", "Cost");
		System.out.println(columns);

		
		while (parkChoice.next()) {
			String sites = String.format("%-15.15s%-15.15s%-15.15s%-15.15s%-15.15s%-15.15s\n", parkChoice.getInt("site_id"), parkChoice.getInt("max_occupancy"), parkChoice.getBoolean("accessible"), 
					parkChoice.getInt("max_rv_length"), parkChoice.getBoolean("utilities"), (price.multiply(tripDays)));
			System.out.println(sites);
		}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("Which site would you like to reserve?");
		String siteReservation = userInput.nextLine();
		System.out.println("What name should the reservation be under?");
		String nameReservation = userInput.nextLine();
		id = Integer.parseInt(siteReservation);
		int x = makeRes (id, nameReservation, arriveDate, departDate);
		
		System.out.println("Your confirmation id is: " + x);
		
		return x;
		
		
	}
	
	public int makeRes(int siteId, String name, Date startDate, Date endDate) {
		
		
		
		String createRes = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date) "
				+ "VALUES (?, ?, ?, ?, now())"; 
		jdbcTemplate.update(createRes, siteId, name, startDate, endDate);
		String resId = "SELECT reservation_id FROM reservation WHERE (name = ? AND from_date = ? AND to_date = ?)";
		SqlRowSet makinRes = jdbcTemplate.queryForRowSet(resId, name, startDate, endDate);
		makinRes.next();
		int confirmId = makinRes.getInt("reservation_id");
		
		return confirmId;
	}
	
	
}
