package org.danielli.xultimate.util;

/**
 * <p>Operations on char primitives and Character objects.</p>
 *
 * <p>This class tries to handle {@code null} input gracefully.
 * An exception will not be thrown for a {@code null} input.
 * Each method documents its behaviour in more detail.</p>
 * 
 * <p>#ThreadSafe#</p>
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.lang3.CharUtils
 */
public class CharUtils {
	
	/**
     * <p>Checks whether the character is ASCII 7 bit.</p>
     *
     * <pre>
     *   CharUtils.isAscii('a')  = true
     *   CharUtils.isAscii('A')  = true
     *   CharUtils.isAscii('3')  = true
     *   CharUtils.isAscii('-')  = true
     *   CharUtils.isAscii('\n') = true
     *   CharUtils.isAscii('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if less than 128
     */
    public static boolean isAscii(char ch) {
        return org.apache.commons.lang3.CharUtils.isAscii(ch);
    }
    
    /**
     * <p>Checks whether the character is ASCII 7 bit printable.</p>
     *
     * <pre>
     *   CharUtils.isAsciiPrintable('a')  = true
     *   CharUtils.isAsciiPrintable('A')  = true
     *   CharUtils.isAsciiPrintable('3')  = true
     *   CharUtils.isAsciiPrintable('-')  = true
     *   CharUtils.isAsciiPrintable('\n') = false
     *   CharUtils.isAsciiPrintable('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if between 32 and 126 inclusive
     */
    public static boolean isAsciiPrintable(char ch) {
        return org.apache.commons.lang3.CharUtils.isAsciiPrintable(ch);
    }
	
    /**
     * <p>Checks whether the character is ASCII 7 bit alphabetic lower case.</p>
     *
     * <pre>
     *   CharUtils.isAsciiAlphaLower('a')  = true
     *   CharUtils.isAsciiAlphaLower('A')  = false
     *   CharUtils.isAsciiAlphaLower('3')  = false
     *   CharUtils.isAsciiAlphaLower('-')  = false
     *   CharUtils.isAsciiAlphaLower('\n') = false
     *   CharUtils.isAsciiAlphaLower('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if between 97 and 122 inclusive
     */
	public static boolean isAsciiAlphaLower(char ch) {
		return org.apache.commons.lang3.CharUtils.isAsciiAlphaLower(ch);
	}
	
    /**
     * <p>Checks whether the character is ASCII 7 bit alphabetic upper case.</p>
     *
     * <pre>
     *   CharUtils.isAsciiAlphaUpper('a')  = false
     *   CharUtils.isAsciiAlphaUpper('A')  = true
     *   CharUtils.isAsciiAlphaUpper('3')  = false
     *   CharUtils.isAsciiAlphaUpper('-')  = false
     *   CharUtils.isAsciiAlphaUpper('\n') = false
     *   CharUtils.isAsciiAlphaUpper('&copy;') = false
     * </pre>
     * 
     * @param ch  the character to check
     * @return true if between 65 and 90 inclusive
     */
	public static boolean isAsciiAlphaUpper(char ch) {
		return org.apache.commons.lang3.CharUtils.isAsciiAlphaUpper(ch);
	}
}
