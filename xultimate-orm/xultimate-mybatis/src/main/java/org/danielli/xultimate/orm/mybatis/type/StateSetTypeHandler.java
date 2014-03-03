package org.danielli.xultimate.orm.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeReference;
import org.danielli.xultimate.jdbc.type.StateSet;

/**
 * StateSet类型处理器，数据端通过TINYINT存储。StateSet是用于替代BIG/SET数据类型，因为他们无法为高效索引查询提供支持。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
//@MappedJdbcTypes({ JdbcType.TINYINT })
public class StateSetTypeHandler extends TypeReference<StateSet> implements TypeHandler<StateSet> {

	@Override
	public void setParameter(PreparedStatement ps, int i, StateSet parameter, JdbcType jdbcType) throws SQLException {
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
			ps.setByte(i, parameter.getValue());
		}
	}

	@Override
	public StateSet getResult(ResultSet rs, String columnName) throws SQLException {
		Byte result = rs.getByte(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			return new StateSet(result);
		}
	}

	@Override
	public StateSet getResult(ResultSet rs, int columnIndex) throws SQLException {
		Byte result = rs.getByte(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			return new StateSet(result);
		}
	}

	@Override
	public StateSet getResult(CallableStatement cs, int columnIndex) throws SQLException {
		Byte result = cs.getByte(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			return new StateSet(result);
		}
	}

}
