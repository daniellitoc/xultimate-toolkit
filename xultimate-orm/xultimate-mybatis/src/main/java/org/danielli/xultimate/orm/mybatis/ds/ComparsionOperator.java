package org.danielli.xultimate.orm.mybatis.ds;

/**
 * 动态查询的比较符操作器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public enum ComparsionOperator {
	EQ, 
	NOT_EQ, 
	LIKE, 
	NOT_LIKE, 
	GT,
	LT,
	GE,
	LE,
	IN,
	BETWEEN;
}
