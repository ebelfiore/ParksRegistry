package com.techelevator.model.jdbc;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO{

	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(BasicDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public int searchReservation(String camp, String arrive, String depart, BigDecimal price) {
		
		Date arriveDate = new Date();
		Date departDate = new Date();
		BigDecimal tripDays;
		
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

		SqlRowSet parkChoice = jdbcTemplate.queryForRowSet(makingReservation, arriveDate, arriveDate, departDate, departDate, arriveDate, departDate);		
		
		System.out.println("Site No. \t Max Occup. \t Accesible? \t Max RV Length \t Utility \t Cost");
		
		while (parkChoice.next()) {
			System.out.println(parkChoice.getInt("site_id") + "\t" + parkChoice.getInt("max_occupancy") + "\t" + parkChoice.getBoolean("accessible") 
			+ "\t" + parkChoice.getInt("max_rv_length") + "\t" + parkChoice.getBoolean("utilities") + "\t" + (price.multiply(tripDays)));
		}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return 2;
	}
	
	
}
