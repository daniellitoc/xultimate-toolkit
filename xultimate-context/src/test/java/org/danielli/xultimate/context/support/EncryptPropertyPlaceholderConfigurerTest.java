package org.danielli.xultimate.context.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext-service-config.xml", "classpath:applicationContext-service-crypto.xml", "classpath:/support/applicationContext-service-support.xml" })
public class EncryptPropertyPlaceholderConfigurerTest {

	@Value("${test.value}")
	private String value;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptPropertyPlaceholderConfigurerTest.class); 
	
	@Test
	public void test() {
		LOGGER.info(value);
	}
}
