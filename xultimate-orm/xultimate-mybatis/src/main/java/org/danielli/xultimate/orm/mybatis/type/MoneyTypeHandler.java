package org.danielli.xultimate.orm.mybatis.type;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class MoneyTypeHandler extends BaseTypeHandler<BigDecimal> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, BigDecimal parameter, JdbcType jdbcType) throws SQLException {
		ps.setLong(i, parameter.multiply(BigDecimal.valueOf(100)).longValue());
	}

	@Override
	public BigDecimal getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return BigDecimal.valueOf(rs.getLong(columnName), 2);
	}

	@Override
	public BigDecimal getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return BigDecimal.valueOf(rs.getLong(columnIndex), 2);
	}

	@Override
	public BigDecimal getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return BigDecimal.valueOf(cs.getLong(columnIndex), 2);
	}
}
