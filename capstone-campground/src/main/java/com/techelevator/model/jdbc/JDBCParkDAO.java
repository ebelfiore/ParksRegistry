package com.techelevator.model.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import com.techelevator.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO{

	private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(BasicDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
}
