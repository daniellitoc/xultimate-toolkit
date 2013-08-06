package org.danielli.xultimate;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeTest.class);
	
	@Test
	public void test() {
		// 创建必需的实例
		DateTime dateTime = new DateTime(2000, 1, 1, 0, 0, 0, 0);
		LOGGER.info("{}", dateTime);
		
		// 向某一个瞬间加上 90 天并输出结果
		dateTime = new DateTime(2000, 1, 1, 0, 0, 0, 0);
		LOGGER.info("{}", dateTime.plusDays(90).toString("E MM/dd/yyyy HH:mm:ss.SSS"));
		
		// 距离 Y2K 45 天之后的某天在下一个月的当前周的最后一天的日期
		dateTime = new DateTime(2000, 1, 1, 0, 0, 0, 0);
		LOGGER.info("{}", dateTime.plusDays(45).plusMonths(1).dayOfWeek().withMaximumValue().toString("E MM/dd/yyyy HH:mm:ss.SSS"));
		
		//  Joda 计算结果转为 JDK 对象
		Date date = dateTime.toDate();
		LOGGER.info("{}", date);
		Calendar calendar = dateTime.toCalendar(Locale.getDefault());
		LOGGER.info("{}", calendar);
		
		
		dateTime = new DateTime(date.getTime());
		LOGGER.info("{}", dateTime);
		dateTime = new DateTime(calendar.getTime());
		LOGGER.info("{}", dateTime);
		
		
		dateTime = new DateTime(date);
		LOGGER.info("{}", dateTime);
		dateTime = new DateTime(calendar);
		LOGGER.info("{}", dateTime);
		

		String timeString = "2006-01-26T13:30:00-06:00";
		dateTime = new DateTime(timeString);
		LOGGER.info("{}", dateTime);
		timeString = "2006-01-26";
		dateTime = new DateTime(timeString);
		LOGGER.info("{}", dateTime);
		
		
		LocalDate localDate = new LocalDate(2009, 9, 6);// September 6, 2009
		LOGGER.info("{}", localDate);
		LocalDate now = new LocalDate();
		LocalDate lastDayOfPreviousMonth = now.minusMonths(1).dayOfMonth().withMaximumValue();
		LOGGER.info("{}", lastDayOfPreviousMonth);
		// 计算 11 月中第一个星期一之后的第一个星期二
		LocalDate electionDate = now.monthOfYear()
				 .setCopy(11)        // November
				 .dayOfMonth()       // Access Day Of Month Property
				 .withMinimumValue() // Get its minimum value
				 .plusDays(6)        // Add 6 days
				 .dayOfWeek()        // Access Day Of Week Property
				 .setCopy("Monday")  // Set to Monday (it will round down)
				 .plusDays(1);       // Gives us Tuesday
		LOGGER.info("{}", electionDate);
		
		LocalTime localTime = new LocalTime(13, 30, 26, 0);// 1:30:26PM
		LOGGER.info("{}", localTime);
		
		LOGGER.info("{}", dateTime.toString(ISODateTimeFormat.basicDateTime()));
		LOGGER.info("{}", dateTime.toString(ISODateTimeFormat.basicDateTimeNoMillis()));
		LOGGER.info("{}", dateTime.toString(ISODateTimeFormat.basicOrdinalDateTime()));
		LOGGER.info("{}", dateTime.toString(ISODateTimeFormat.basicWeekDateTime()));
		
		LOGGER.info("{}", dateTime.toString("MM/dd/yyyy hh:mm:ss.SSSa"));
		LOGGER.info("{}", dateTime.toString("dd-MM-yyyy HH:mm:ss"));
		LOGGER.info("{}", dateTime.toString("EEEE dd MMMM, yyyy HH:mm:ssa"));
		LOGGER.info("{}", dateTime.toString("MM/dd/yyyy HH:mm ZZZZ"));
		LOGGER.info("{}", dateTime.toString("MM/dd/yyyy HH:mm Z"));	}
}
