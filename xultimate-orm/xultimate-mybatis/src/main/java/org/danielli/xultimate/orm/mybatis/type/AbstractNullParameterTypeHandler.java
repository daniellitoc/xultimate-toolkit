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

/**
 * 抽象空参数类型处理器。提供一个默认值，设置参数时，将null替换为默认值；读取值时，若为默认值，则替换会null值，保持Java端的对象为null，数据库存储为默认值，此方式便于构建数据库高效索引，避免NULL值情况。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * 
 * @param <T> 操作对象Java类型。
 */
public abstract class AbstractNullParameterTypeHandler<T> extends TypeReference<T> implements TypeHandler<T> {

	protected Configuration configuration;

	public void setConfiguration(Configuration c) {
		this.configuration = c;
	}

	protected T nullParameter;

	public void setNullParameter(T nullParameter) {
		this.nullParameter = nullParameter;
	}

	public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			parameter = nullParameter;
		}

		if (parameter == null) {
			if (jdbcType == null) {
				throw new TypeException("JDBC requires that the JdbcType must be specified for all nullable parameters.");
			}
			try {
				ps.setNull(i, jdbcType.TYPE_CODE);
			} catch (SQLException e) {
				throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . " + "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. " + "Cause: " + e, e);
			}
		} else {
			setNonNullParameter(ps, i, parameter, jdbcType);
		}
	}

	public T getResult(ResultSet rs, String columnName) throws SQLException {
		T result = getNullableResult(rs, columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			if (result.equals(nullParameter)) {
				return null;
			} else {
				return result;
			}
		}
	}

	public T getResult(ResultSet rs, int columnIndex) throws SQLException {
		T result = getNullableResult(rs, columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			if (result.equals(nullParameter)) {
				return null;
			} else {
				return result;
			}
		}
	}

	public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
		T result = getNullableResult(cs, columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			if (result.equals(nullParameter)) {
				return null;
			} else {
				return result;
			}
		}
	}

	public abstract void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;

	public abstract T getNullableResult(ResultSet rs, String columnName) throws SQLException;

	public abstract T getNullableResult(ResultSet rs, int columnIndex) throws SQLException;

	public abstract T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException;

}
