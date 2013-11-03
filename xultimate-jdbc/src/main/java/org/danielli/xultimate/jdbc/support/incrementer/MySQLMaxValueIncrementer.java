package org.danielli.xultimate.jdbc.support.incrementer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.danielli.xultimate.util.Assert;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.incrementer.AbstractColumnMaxValueIncrementer;

/**
 * MySQL最大值增长器。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 * @see AbstractColumnMaxValueIncrementer
 */
public class MySQLMaxValueIncrementer extends AbstractColumnMaxValueIncrementer {
	
	/** 获取新插入主键值的SQL语句。 */
	private static final String VALUE_SQL = "select last_insert_id()";

	/** 此次请求的下一个值 */
	private long nextId = 0;

	/** 此次请求的最大值 */
	private long maxId = 0;
	
	/** 此次请求的步进 */
	private long step = 1;
	
	private ReentrantLock reentrantLock = new ReentrantLock();


	/**
	 * Default constructor for bean property style usage.
	 * @see #setDataSource
	 * @see #setIncrementerName
	 * @see #setColumnName
	 */
	public MySQLMaxValueIncrementer() {
	}

	/**
	 * 创建实例。
	 * 
	 * @param dataSource 使用的数据源。
	 * @param incrementerName 使用的表名称。
	 * @param columnName 使用的表中列名称。
	 */
	public MySQLMaxValueIncrementer(DataSource dataSource, String incrementerName, String columnName) {
		super(dataSource, incrementerName, columnName);
	}

	@Override
	public void afterPropertiesSet() {
		Assert.isTrue(this.step > 0, "Property 'step' must greater than 0");
		Assert.isTrue(this.getCacheSize() > 0, "Property 'cacheSize' must greater than 0");
		super.afterPropertiesSet();
	}

	@Override
	protected long getNextKey() throws DataAccessException {
		reentrantLock.lock();
		try {
			this.nextId += this.step;
			if (this.maxId < this.nextId) {
				/*
				* Need to use straight JDBC code because we need to make sure that the insert and select
				* are performed on the same connection (otherwise we can't be sure that last_insert_id()
				* returned the correct value)
				*/
				Connection con = DataSourceUtils.getConnection(getDataSource());
				Statement stmt = null;
				try {
					stmt = con.createStatement();
					DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
					// Increment the sequence column...
					String columnName = getColumnName();
					stmt.executeUpdate("update "+ getIncrementerName() + " set " + columnName +
							" = last_insert_id(" + columnName + " + " + (getCacheSize() * this.step) + ")");
					// Retrieve the new max of the sequence column...
					ResultSet rs = stmt.executeQuery(VALUE_SQL);
					try {
						if (!rs.next()) {
							throw new DataAccessResourceFailureException("last_insert_id() failed after executing an update");
						}
						this.maxId = rs.getLong(1);
					}
					finally {
						JdbcUtils.closeResultSet(rs);
					}
					this.nextId = this.maxId - getCacheSize() * this.step + 1;
				}
				catch (SQLException ex) {
					throw new DataAccessResourceFailureException("Could not obtain last_insert_id()", ex);
				}
				finally {
					JdbcUtils.closeStatement(stmt);
					DataSourceUtils.releaseConnection(con, getDataSource());
				}
			}
			return this.nextId;
		} finally {
			reentrantLock.unlock();
		}
	}

	/**
	 * 获取步进。
	 */
	public long getStep() {
		return step;
	}

	/**
	 * 设置步进。
	 */
	public void setStep(long step) {
		this.step = step;
	}
}
