package org.danielli.xultimate.orm.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.danielli.xultimate.jdbc.type.CodeEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link org.danielli.xultimate.jdbc.type.CodeEnum}类型转换器，替代{@link org.apache.ibatis.type.EnumOrdinalTypeHandler}。
 *
 * @author Daniel Li
 * @since 8 August 2015
 */
public class CodeEnumOrdinalTypeHandler<E extends CodeEnum> extends BaseTypeHandler<E> {

    private Class<E> type;
    private final E[] enums;

    public CodeEnumOrdinalTypeHandler(Class<E> type) {
        if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null)
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return valueOf(i);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return valueOf(i);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return valueOf(i);
        }
    }

    private E valueOf(int code) {
        for (E element : enums) {
            if (element.getCode() == code) {
                return element;
            }
        }
        throw new IllegalArgumentException("Cannot convert " + code + " to " + type.getSimpleName() + " by code value.");
    }
}
