package org.danielli.xultimate.orm.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeReference;

/**
 * Java端为Boolean类型，数据库端采用CHAR(0)数据类型。若为true，则存储为""；若为false，则存储为NULL。此方式避免数据类型占用存储空间的问题。切记，此方式无法建立索引，所以不适合作为查询条件(会进行全表扫描)。若需要构建索引便于查询，请使用{@code org.apache.ibatis.type.BooleanTypeHandler}。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
//@MappedJdbcTypes({ JdbcType.CHAR })
public class BooleanTypeHandler extends TypeReference<Boolean> implements TypeHandler<Boolean> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . " + "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. " + "Cause: this argument is required; it must not be null");
		}
		if (jdbcType == null) {
			throw new TypeException("JDBC requires that the JdbcType must be specified for all nullable parameters.");
		}
		if (parameter) {
			ps.setString(i, "");
		} else {
			try {
				ps.setNull(i, jdbcType.TYPE_CODE);
			} catch (SQLException e) {
				throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . " + "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. " + "Cause: " + e, e);
			}
		}
	}

	@Override
	public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
		rs.getString(columnName);
		if (rs.wasNull()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
		rs.getString(columnIndex);
		if (rs.wasNull()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
		cs.getString(columnIndex);
		if (cs.wasNull()) {
			return false;
		} else {
			return true;
		}
	}

}
