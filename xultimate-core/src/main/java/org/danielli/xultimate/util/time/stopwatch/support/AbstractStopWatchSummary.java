package org.danielli.xultimate.util.time.stopwatch.support;

import org.danielli.xultimate.util.time.stopwatch.StopWatchSummary;
import org.slf4j.Logger;

/**
 * 抽象秒表汇总器。
 * 
 * @author Daniel Li
 * @since 17 Jun 2013
 */
public abstract class AbstractStopWatchSummary implements StopWatchSummary {
	
	private Logger logger;
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Logger getLogger() {
		return logger;
	}
}
