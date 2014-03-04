package org.danielli.xultimate.context.performance;

import org.danielli.xultimate.util.time.stopwatch.support.AbstractStopWatchSummary;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;

/**
 * 抽象秒表监测拦截器。
 *
 * <p>Uses a {@code StopWatch} for the actual performance measuring.
 *
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see PerformanceMonitorInterceptor
 */
public abstract class AbstractStopWatchMonitoringInterceptor extends AbstractMonitoringInterceptor {

	protected AbstractStopWatchSummary stopWatchSummary;
	
	public void setStopWatchSummary(AbstractStopWatchSummary stopWatchSummary) {
		this.stopWatchSummary = stopWatchSummary;
	}
}
