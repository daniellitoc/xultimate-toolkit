package org.danielli.xultimate.util.math;

/**
 * <p>Provides extra functionality for Java Number classes.</p>
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.lang3.math.NumberUtils
 */
public class NumberUtils {
	
    /**
     * <p>Convert a <code>String</code> to an <code>int</code>, returning
     * <code>zero</code> if the conversion fails.</p>
     *
     * <p>If the string is <code>null</code>, <code>zero</code> is returned.</p>
     *
     * <pre>
     *   NumberUtils.toInt(null) = 0
     *   NumberUtils.toInt("")   = 0
     *   NumberUtils.toInt("1")  = 1
     * </pre>
     *
     * @param str  the string to convert, may be null
     * @return the int represented by the string, or <code>zero</code> if
     *  conversion fails
     */
	public static int toInt(String str) {
		return org.apache.commons.lang3.math.NumberUtils.toInt(str);
	}
	
	/**
	 * 判断是否是正数。
	 * 
	 * @param number 数字
	 * @return 如果是返回true，否则返回false。
	 */
	public static boolean isPositiveNumber(Number number) {
		return org.apache.commons.lang3.math.NumberUtils.isDigits(number.toString()) && number.byteValue() > 0;
	}
}
