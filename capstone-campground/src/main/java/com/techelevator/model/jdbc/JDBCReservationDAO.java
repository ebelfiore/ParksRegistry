package com.techelevator.model.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO{

	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(BasicDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public int makeReservation(String camp, String arrive, String depart) {
		
		
		String makingReservation = "SELECT * FROM site WHERE site.site_id not in ("
				+ "SELECT site.site_id FROM site"
				+ "JOIN reservation on site.site_id = reservation.site_id WHERE (((? >= from_date) AND (? <= to_date))"
				+ " OR ((? >= from_date) AND (? <= to_date)) OR ((? <= from_date) AND (? >= to_date))) LIMIT 5";
		SqlRowSet parkChoice = jdbcTemplate.queryForRowSet(makingReservation, arrive, arrive, depart, depart, arrive, depart);		
		
		return 2;
	}
	
	
}
