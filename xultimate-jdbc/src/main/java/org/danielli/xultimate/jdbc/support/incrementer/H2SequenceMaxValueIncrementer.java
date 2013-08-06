package org.danielli.xultimate.jdbc.support.incrementer;

import javax.sql.DataSource;

/**
 * H2序列最大值增长器。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 * @see AbstractSequenceMaxValueIncrementer
 */
public class H2SequenceMaxValueIncrementer extends AbstractSequenceMaxValueIncrementer{

	/**
	 * 创建实例。并通过相应的setter方法完成必要设置。
	 * 
	 * @see #setDataSource
	 * @see #setIncrementerName
	 */
	public H2SequenceMaxValueIncrementer() {
	}

	/**
	 * 创建实例。
	 * 
	 * @param dataSource 使用的数据源。
	 * @param incrementerName 使用的序列名称。
	 */
	public H2SequenceMaxValueIncrementer(DataSource dataSource, String incrementerName) {
		super(dataSource, incrementerName);
	}
	
	@Override
	protected String getSequenceQuery() {
		return "select " + getIncrementerName() + ".nextval from dual";
	}

}
