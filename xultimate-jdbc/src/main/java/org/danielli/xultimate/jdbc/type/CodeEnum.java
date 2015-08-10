package org.danielli.xultimate.jdbc.type;

import java.io.Serializable;

/**
 * 带有Code的枚举类型。
 *
 * @author Daniel Li
 * @since 8 August 2015
 */
public interface CodeEnum<E extends java.lang.Enum<E>> extends Comparable<E>, Serializable {

    int getCode();
}