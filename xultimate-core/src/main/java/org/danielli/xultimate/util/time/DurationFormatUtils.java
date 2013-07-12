package org.danielli.xultimate.util.time;

/**
 * <p>Duration formatting utilities and constants. The following table describes the tokens 
 * used in the pattern language for formatting. </p>
 * <table border="1">
 *  <tr><th>character</th><th>duration element</th></tr>
 *  <tr><td>y</td><td>years</td></tr>
 *  <tr><td>M</td><td>months</td></tr>
 *  <tr><td>d</td><td>days</td></tr>
 *  <tr><td>H</td><td>hours</td></tr>
 *  <tr><td>m</td><td>minutes</td></tr>
 *  <tr><td>s</td><td>seconds</td></tr>
 *  <tr><td>S</td><td>milliseconds</td></tr>
 * </table>
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.lang3.time.DurationFormatUtils
 */
public class DurationFormatUtils {
	
    /**
     * <p>Formats the time gap as a string, using the specified format, and padding with zeros and 
     * using the default timezone.</p>
     * 
     * <p>This method formats durations using the days and lower fields of the
     * format pattern. Months and larger are not used.</p>
     * 
     * @param durationMillis  the duration to format
     * @param format  the way in which to format the duration, not null
     * @return the formatted duration, not null
     */
	public static String formatDuration(long durationMillis, String format) {
		return org.apache.commons.lang3.time.DurationFormatUtils.formatDuration(durationMillis, format);
	}
}
