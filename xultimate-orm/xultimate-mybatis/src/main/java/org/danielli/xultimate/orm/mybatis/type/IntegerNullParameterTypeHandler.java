package org.danielli.xultimate.orm.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;

/**
 * Integer空参数类型处理器。提供一个默认值(-1)，设置参数时，将null替换为-1；读取值时，若为-1，则替换会null值，保持Java端的对象为null，数据库存储为-1，此方式便于构建数据库高效索引，避免NULL值情况。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
//@MappedJdbcTypes({ JdbcType.INTEGER, JdbcType.SMALLINT })
public class IntegerNullParameterTypeHandler extends AbstractNullParameterTypeHandler<Integer> {
	
	public IntegerNullParameterTypeHandler() {
		setNullParameter(-1);
	}
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Integer parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter);
	}

	@Override
	public Integer getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getInt(columnName);
	}

	@Override
	public Integer getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getInt(columnIndex);
	}

	@Override
	public Integer getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getInt(columnIndex);
	}
}
