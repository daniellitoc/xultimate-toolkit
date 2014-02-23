package org.danielli.xultimate.util.time;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChineseCalendarGBTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChineseCalendarGBTest.class);
	
	@Test
	public void test() {
		ChineseCalendarGB c = new ChineseCalendarGB();
		String cmd = "day";
		int y = 2014;
		int m = 2;
		int d = 23;
		c.setGregorian(y, m, d);
		c.computeChineseFields();
		c.computeSolarTerms();
		if (cmd.equalsIgnoreCase("year")) {
			String[] t = c.getYearTable();
			for (int i = 0; i < t.length; i++)
				LOGGER.info(t[i]);
		} else if (cmd.equalsIgnoreCase("month")) {
			String[] t = c.getMonthTable();
			for (int i = 0; i < t.length; i++)
				LOGGER.info(t[i]);
		} else {
			LOGGER.info(c.toString());
			LOGGER.info(c.getDateString());
		}
	}
}
