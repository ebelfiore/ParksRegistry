package com.techelevator.model.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import com.techelevator.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO{

	private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(BasicDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
}
