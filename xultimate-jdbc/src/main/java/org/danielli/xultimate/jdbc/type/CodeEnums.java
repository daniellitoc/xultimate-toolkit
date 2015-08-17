package org.danielli.xultimate.jdbc.type;

/**
 * CodeEnum工具。
 *
 * @author Daniel Li
 * @since 17 August 2015
 */
public class CodeEnums {

    public static <E extends CodeEnum> E valueOf(Class<E> clazz, int code) {
        E[] codeEnums = clazz.getEnumConstants();
        for (E codeEnum : codeEnums) {
            if (codeEnum.getCode() == code) {
                return codeEnum;
            }
        }
        return null;
    }
}
