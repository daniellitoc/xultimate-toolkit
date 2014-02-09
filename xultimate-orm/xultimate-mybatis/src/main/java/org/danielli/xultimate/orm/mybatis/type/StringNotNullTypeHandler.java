package org.danielli.xultimate.orm.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;

public class StringNotNullTypeHandler extends BaseNotNullTypeHandler<String> {

	public StringNotNullTypeHandler() {
		super("");
	}
	
	  @Override
	  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
	      throws SQLException {
	    ps.setString(i, parameter);
	  }

	  @Override
	  public String getNullableResult(ResultSet rs, String columnName)
	      throws SQLException {
	    return rs.getString(columnName);
	  }

	  @Override
	  public String getNullableResult(ResultSet rs, int columnIndex)
	      throws SQLException {
	    return rs.getString(columnIndex);
	  }

	  @Override
	  public String getNullableResult(CallableStatement cs, int columnIndex)
	      throws SQLException {
	    return cs.getString(columnIndex);
	  }
	}

