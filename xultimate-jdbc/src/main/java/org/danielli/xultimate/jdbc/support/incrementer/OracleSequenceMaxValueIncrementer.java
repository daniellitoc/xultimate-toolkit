package org.danielli.xultimate.jdbc.support.incrementer;

import javax.sql.DataSource;

/**
 * Oracle序列最大值增长器。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 * @see AbstractSequenceMaxValueIncrementer
 */
public class OracleSequenceMaxValueIncrementer extends AbstractSequenceMaxValueIncrementer{

	/**
	 * 创建实例。并通过相应的setter方法完成必要设置。
	 * 
	 * @see #setDataSource
	 * @see #setIncrementerName
	 */
	public OracleSequenceMaxValueIncrementer() {
	}

	/**
	 * 创建实例。
	 * 
	 * @param dataSource 使用的数据源。
	 * @param incrementerName 使用的序列名称。
	 */
	public OracleSequenceMaxValueIncrementer(DataSource dataSource, String incrementerName) {
		super(dataSource, incrementerName);
	}


	@Override
	protected String getSequenceQuery() {
		return "select " + getIncrementerName() + ".nextval from dual";
	}


}
