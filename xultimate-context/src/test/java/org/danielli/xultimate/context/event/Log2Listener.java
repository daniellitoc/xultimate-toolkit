package org.danielli.xultimate.context.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * 日志监听器，当使用ApplicationContext.publishEvent(LogEvent)时，监听器起作用。
 * @author Daniel Li
 * @since 29 July 2012
 */
@Service
public class Log2Listener implements ApplicationListener<LogEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(Log2Listener.class);
	
	@Override
	public void onApplicationEvent(LogEvent event) {
		String message = event.getMessage();
		LOGGER.info("{}:{}", Thread.currentThread(), message);
	}

}
