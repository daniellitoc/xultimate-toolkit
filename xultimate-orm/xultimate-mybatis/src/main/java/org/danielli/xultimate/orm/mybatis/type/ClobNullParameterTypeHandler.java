package org.danielli.xultimate.orm.mybatis.type;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;

/**
 * CLOB空参数类型处理器。提供一个默认值("")，设置参数时，将null替换为""；读取值时，若为""，则替换会null值，保持Java端的对象为null，数据库存储为""，此方式便于构建数据库高效索引，避免NULL值情况。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
//@MappedJdbcTypes({ JdbcType.CLOB })
public class ClobNullParameterTypeHandler extends AbstractNullParameterTypeHandler<String> {

	public ClobNullParameterTypeHandler() {
		setNullParameter("");
	}
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		StringReader reader = new StringReader(parameter);
	    ps.setCharacterStream(i, reader, parameter.length());
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String value = "";
	    Clob clob = rs.getClob(columnName);
	    if (clob != null) {
	      int size = (int) clob.length();
	      value = clob.getSubString(1, size);
	    }
	    return value;
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String value = "";
	    Clob clob = rs.getClob(columnIndex);
	    if (clob != null) {
	      int size = (int) clob.length();
	      value = clob.getSubString(1, size);
	    }
	    return value;
	}

	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String value = "";
	    Clob clob = cs.getClob(columnIndex);
	    if (clob != null) {
	      int size = (int) clob.length();
	      value = clob.getSubString(1, size);
	    }
	    return value;
	}

}
