package org.danielli.xultimate.context.i18n;

import java.util.Locale;

import org.danielli.xultimate.context.util.ApplicationContextUtils;
import org.danielli.xultimate.context.util.BeanFactoryContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/i18n/applicationContext-service-i18n.xml", "classpath:/applicationContext-service-util.xml" })
public class I18nTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(I18nTest.class);
	
	@Test
	public void test() {
		ApplicationContext applicationContext = BeanFactoryContext.currentApplicationContext();
		LOGGER.info(ApplicationContextUtils.getMessage(applicationContext, Locale.CHINA, "helloWorld"));
		LOGGER.info(ApplicationContextUtils.getMessage(applicationContext, Locale.US, "helloWorld"));
		if (ApplicationContextUtils.getMessage(applicationContext, Locale.CHINA, "helloWorld1") == null) {
			LOGGER.info("helloWorld1 is not existsed");
		}
		
	}

}
