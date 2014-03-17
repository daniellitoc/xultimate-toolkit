package org.danielli.xultimate.transaction.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.danielli.xultimate.jdbc.datasource.lookup.RoutingDataSourceUtils;
import org.danielli.xultimate.transaction.dao.UserDAO;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("userDAOImpl")
public class UserDAOImpl implements UserDAO {

	@Resource(name = "routingDataSourceMap")
	private Map<String, DataSource> routingDataSourceMap;
	
	@Override
	@Transactional(value = "chainTransactionManager", propagation = Propagation.REQUIRED)
	public void saveUser() {
		System.out.println("Save User");
		DataSource dataSource = routingDataSourceMap.get(RoutingDataSourceUtils.getRoutingDataSourceKey());
		Connection con = DataSourceUtils.getConnection(dataSource);
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.execute("INSERT INTO `user` values (1, 'Daniel Li')");
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
		finally {
			JdbcUtils.closeStatement(stmt);
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}
}
