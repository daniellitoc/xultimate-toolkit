package org.danielli.xultimate;

import java.util.Locale;

import org.joda.time.DateTime;
import org.junit.Test;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrettyTimeTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrettyTimeTest.class);
	
	@Test
	public void test() {
		PrettyTime p = new PrettyTime(Locale.CHINA);
		
		LOGGER.info(p.format(new DateTime().toDate()));
		//prints: “right now”

		LOGGER.info(p.format(new DateTime().minusMinutes(10).toDate()));
	}
}
