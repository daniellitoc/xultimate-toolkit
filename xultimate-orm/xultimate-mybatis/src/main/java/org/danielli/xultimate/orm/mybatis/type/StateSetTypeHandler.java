package org.danielli.xultimate.orm.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.danielli.xultimate.jdbc.type.StateSet;

public class StateSetTypeHandler extends BaseTypeHandler<StateSet> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, StateSet parameter, JdbcType jdbcType) throws SQLException {
		ps.setLong(i, parameter.getValue());
	}

	@Override
	public StateSet getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return new StateSet(rs.getLong(columnName));
	}

	@Override
	public StateSet getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return new StateSet(columnIndex);
	}

	@Override
	public StateSet getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return new StateSet(cs.getLong(columnIndex));
	}
}
