package org.danielli.xultimate.context.config;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-config.xml" })
public class CustomCardEditorTest {
	
	@Resource
	private Boss boss;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomCardEditorTest.class);
	
	@Test
	public void test() {
		LOGGER.info("Brand:" + boss.getCar().getBrand());
		LOGGER.info("MaxSpeed:" + boss.getCar().getMaxSpeed());
		LOGGER.info("Price:" + boss.getCar().getPrice());
	}
}
