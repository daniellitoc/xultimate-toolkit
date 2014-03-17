package org.danielli.xultimate.transaction.dao.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.transaction.dao.OtherDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("otherDAOImpl")
public class OtherDAOImpl implements OtherDAO {
	
	@Resource(name = "routingJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	@Transactional(value = "chainTransactionManager", propagation = Propagation.REQUIRED)
	public void saveOther() {
		System.out.println("Save Other");
		jdbcTemplate.update("INSERT INTO `other` values (1, 'Daniel Li')");
	}
	
	@Override
	@Transactional(value = "chainTransactionManager", propagation = Propagation.REQUIRED)
	public void saveErrorOther() {
		throw new RuntimeException();
	}
}
