package org.danielli.xultimate.orm.mybatis.type;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeReference;

/**
 * Java端为BigDecimal类型，数据库端采用BIGINT数据类型。通过scale自动处理转化，计算操作也是在Java端进行，保存数据库处于IO密集型，Java端处于CPU密集型。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
//@MappedJdbcTypes({ JdbcType.BIGINT, JdbcType.INTEGER })
public class BigDecimalTypeHandler extends TypeReference<BigDecimal> implements TypeHandler<BigDecimal> {

	protected int scale;
	
	public BigDecimalTypeHandler() {
		this(4);
	}
	
	public BigDecimalTypeHandler(int scale) {
		this.scale = scale;
	}

	@Override
	public void setParameter(PreparedStatement ps, int i, BigDecimal parameter, JdbcType jdbcType) throws SQLException {
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
			ps.setLong(i, parameter.multiply(BigDecimal.valueOf(Math.pow(10, scale))).longValue());
		}
	}

	@Override
	public BigDecimal getResult(ResultSet rs, String columnName) throws SQLException {
		Long result = rs.getLong(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			return BigDecimal.valueOf(result, scale);
		}
	}

	@Override
	public BigDecimal getResult(ResultSet rs, int columnIndex) throws SQLException {
		Long result = rs.getLong(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			return BigDecimal.valueOf(result, scale);
		}
	}

	@Override
	public BigDecimal getResult(CallableStatement cs, int columnIndex) throws SQLException {
		Long result = cs.getLong(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			return BigDecimal.valueOf(result, scale);
		}
	}

}
