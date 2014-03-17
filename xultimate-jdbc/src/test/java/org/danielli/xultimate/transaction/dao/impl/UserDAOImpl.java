package org.danielli.xultimate.transaction.dao.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.transaction.dao.UserDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDAOImpl")
public class UserDAOImpl implements UserDAO {

	@Resource(name = "routingJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Override
	@Transactional(value = "chainTransactionManager", propagation = Propagation.REQUIRED)
	public void saveUser() {
		System.out.println("Save User");
		jdbcTemplate.update("INSERT INTO `user` values (1, 'Daniel Li')");
	}
}
