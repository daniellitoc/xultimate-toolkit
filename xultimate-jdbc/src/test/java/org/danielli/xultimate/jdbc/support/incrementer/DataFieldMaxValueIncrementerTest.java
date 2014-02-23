package org.danielli.xultimate.jdbc.support.incrementer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:/applicationContext-service-config.xml",
		"classpath:/applicationContext-service-crypto.xml",
		"classpath:/applicationContext-dao-base.xml",
		"classpath:/primaryKey/applicationContext-dao-primaryKey.xml"
	})
public class DataFieldMaxValueIncrementerTest {

	@Resource(name = "primaryKey1Incrementer")
	private DataFieldMaxValueIncrementer incrementer1;
	
	@Resource(name = "primaryKey2Incrementer")
	private DataFieldMaxValueIncrementer incrementer2;
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(DataFieldMaxValueIncrementerTest.class);
	
	@Test
	public void test() {
		
		for (int i = 0 ; i < 100; i++) {
			LOGGER.info("{}", incrementer1.nextLongValue());
		}
		for (int i = 0 ; i < 100; i++) {
			LOGGER.info("{}", incrementer2.nextLongValue());
		}
	}
}
