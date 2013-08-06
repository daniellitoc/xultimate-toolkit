package org.danielli.xultimate.orm.jpa.ds;

import java.io.Serializable;

/**
 * 动态查询的操作项。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 *
 * @param <T> 值。
 */
public class Item<T> implements Serializable {
	private static final long serialVersionUID = -730615840191522967L;

	private String key;
	
	private ComparsionOperator comparsionOperator;
	
	private Value<T> value;
	
	private LogicalOperator logicalOperator;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ComparsionOperator getComparsionOperator() {
		return comparsionOperator;
	}

	public void setComparsionOperator(ComparsionOperator comparsionOperator) {
		this.comparsionOperator = comparsionOperator;
	}

	public Value<T> getValue() {
		return value;
	}

	public void setValue(Value<T> value) {
		this.value = value;
	}

	public LogicalOperator getLogicalOperator() {
		return logicalOperator;
	}

	public void setLogicalOperator(LogicalOperator logicalOperator) {
		this.logicalOperator = logicalOperator;
	}

}
