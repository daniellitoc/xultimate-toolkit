package org.danielli.xultimate.context.scheduling.quartz;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleBusinessObject {

	private static Logger logger = LoggerFactory.getLogger(ExampleBusinessObject.class); 
	
	public void doIt() {
		logger.info("{}", new Date());
	}
}
