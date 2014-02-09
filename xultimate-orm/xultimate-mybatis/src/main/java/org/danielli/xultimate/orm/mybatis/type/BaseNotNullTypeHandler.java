package org.danielli.xultimate.orm.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeReference;

public abstract class BaseNotNullTypeHandler<T> extends TypeReference<T> implements TypeHandler<T> {

	protected Configuration configuration;

	public void setConfiguration(Configuration c) {
		this.configuration = c;
	}
	
	protected T defaultParameter;
	
	public BaseNotNullTypeHandler(T defaultParameter) {
		this.defaultParameter = defaultParameter;
	}

	public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			if (jdbcType == null) {
				throw new TypeException(
						"JDBC requires that the JdbcType must be specified for all nullable parameters.");
			}
			parameter = defaultParameter;
		}
		setNonNullParameter(ps, i, parameter, jdbcType);
	}

	public T getResult(ResultSet rs, String columnName) throws SQLException {
		T result = getNullableResult(rs, columnName);
		if (defaultParameter.equals(result)) {
			return null;
		} else {
			return result;
		}
	}

	public T getResult(ResultSet rs, int columnIndex) throws SQLException {
		T result = getNullableResult(rs, columnIndex);
		if (defaultParameter.equals(result)) {
			return null;
		} else {
			return result;
		}
	}

	public T getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		T result = getNullableResult(cs, columnIndex);
		if (defaultParameter.equals(result)) {
			return null;
		} else {
			return result;
		}
	}

	public abstract void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;

	public abstract T getNullableResult(ResultSet rs, String columnName) throws SQLException;

	public abstract T getNullableResult(ResultSet rs, int columnIndex) throws SQLException;

	public abstract T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException;

}
