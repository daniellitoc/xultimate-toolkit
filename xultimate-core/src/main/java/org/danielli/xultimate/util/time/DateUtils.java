package org.danielli.xultimate.util.time;

/**
 * <p>A suite of utilities surrounding the use of the
 * {@link java.util.Calendar} and {@link java.util.Date} object.</p>
 * 
 * <p>DateUtils contains a lot of common methods considering manipulations
 * of Dates or Calendars. Some methods require some extra explanation.
 * The truncate, ceiling and round methods could be considered the Math.floor(),
 * Math.ceil() or Math.round versions for dates
 * This way date-fields will be ignored in bottom-up order.
 * As a complement to these methods we've introduced some fragment-methods.
 * With these methods the Date-fields will be ignored in top-down order.
 * Since a date without a year is not a valid date, you have to decide in what
 * kind of date-field you want your result, for instance milliseconds or days.
 * </p>
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see org.apache.commons.lang3.time.DateUtils
 */
public class DateUtils {
	
	/**
     * 微秒与毫秒的转换值。
     */
    public static final long MICROS_PER_MILLIS = 1000L;
    
    /**
     * 纳秒与微秒的转换值。
     */
    public static final long NANOS_PER_MICROS = 1000L;
	
	/**
	 *  纳秒与毫秒的转换值。
	 */
    public static final long NANOS_PER_MILLIS = NANOS_PER_MICROS * MICROS_PER_MILLIS;
    
    /**
     * Number of milliseconds in a standard second.
     * @since 2.1
     */
    public static final long MILLIS_PER_SECOND = 1000;
    /**
     * Number of milliseconds in a standard minute.
     * @since 2.1
     */
    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    /**
     * Number of milliseconds in a standard hour.
     * @since 2.1
     */
    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    /**
     * Number of milliseconds in a standard day.
     * @since 2.1
     */
    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;
    
    
}
