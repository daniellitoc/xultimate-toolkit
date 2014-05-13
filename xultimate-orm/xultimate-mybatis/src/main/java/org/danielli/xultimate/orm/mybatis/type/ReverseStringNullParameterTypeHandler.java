package org.danielli.xultimate.orm.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.danielli.xultimate.util.StringUtils;

/**
 * String反转空参数类型处理器。用于实现后缀索引。提供一个默认值("")，设置参数时，将null替换为""；读取值时，若为""，则替换会null值，保持Java端的对象为null，数据库存储为""，此方式便于构建数据库高效索引，避免NULL值情况。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
//@MappedJdbcTypes({ JdbcType.CHAR, JdbcType.VARCHAR })
public class ReverseStringNullParameterTypeHandler extends AbstractNullParameterTypeHandler<String> {

	public ReverseStringNullParameterTypeHandler() {
		setNullParameter("");
	}
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, StringUtils.reverse(parameter));
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return StringUtils.reverse(rs.getString(columnName));
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return StringUtils.reverse(rs.getString(columnIndex));
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return StringUtils.reverse(cs.getString(columnIndex));
	}

}
