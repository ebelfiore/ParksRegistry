package com.techelevator.model.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import com.techelevator.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO{

	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(BasicDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
