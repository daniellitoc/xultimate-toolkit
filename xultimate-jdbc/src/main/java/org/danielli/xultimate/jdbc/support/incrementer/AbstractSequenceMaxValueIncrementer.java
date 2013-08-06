package org.danielli.xultimate.jdbc.support.incrementer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;

/**
 * 抽象序列最大值增长器。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 * @see AbstractDataFieldMaxValueIncrementer
 */
public abstract class AbstractSequenceMaxValueIncrementer extends AbstractDataFieldMaxValueIncrementer{

	/** 此次请求的下一个值 */
	private long nextId = 0;

	/** 此次请求的最大值 */
	private long maxId = 0;
	
	/** 此次请求的步进 */
	private long step = 1;
	
	/** 请此请求缓存的个数 */
	private int cacheSize = 1; 

	/**
	 * 创建实例。并通过相应的setter方法完成必要设置。
	 * 
	 * @see #setDataSource
	 * @see #setIncrementerName
	 */
	public AbstractSequenceMaxValueIncrementer() {
	}
	
	/**
	 * 创建实例。
	 * 
	 * @param dataSource 使用的数据源。
	 * @param incrementerName 使用的序列名称。
	 */
	public AbstractSequenceMaxValueIncrementer(DataSource dataSource, String incrementerName) {
		super(dataSource, incrementerName);
	}
	
	@Override
	protected synchronized long getNextKey() {
		if (this.maxId <= this.nextId + this.step) {
			Connection con = DataSourceUtils.getConnection(getDataSource());
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = con.createStatement();
				DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
				rs = stmt.executeQuery(getSequenceQuery());
				if (rs.next()) {
					this.maxId = rs.getLong(1);
				}
				else {
					throw new DataAccessResourceFailureException("Sequence query did not return a result");
				}
			}
			catch (SQLException ex) {
				throw new DataAccessResourceFailureException("Could not obtain sequence value", ex);
			}
			finally {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(stmt);
				DataSourceUtils.releaseConnection(con, getDataSource());
			}
			this.nextId = this.maxId - this.cacheSize * this.step;
		} else {
			this.nextId = this.nextId + this.step;
		}
		return this.nextId;
	}
	
	/**
	 * 查询序列获取下一个值的语句。
	 */
	protected abstract String getSequenceQuery();
	
	/**
	 * 设置缓存个数。
	 */
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	/**
	 * 返回缓存个数。
	 */
	public int getCacheSize() {
		return this.cacheSize;
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
