package org.danielli.xultimate.context.performance;

import org.aopalliance.intercept.MethodInvocation;
import org.danielli.xultimate.util.time.stopwatch.StopWatch;
import org.danielli.xultimate.util.time.stopwatch.support.AbstractStopWatchSummary;
import org.slf4j.Logger;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;

/**
 * Simple AOP Alliance {@code MethodInterceptor} for performance monitoring.
 * This interceptor has no effect on the intercepted method call.
 *
 * <p>Uses a {@code StopWatch} for the actual performance measuring.
 *
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see PerformanceMonitorInterceptor
 */
public class PerformanceMonitorForEachMethodInterceptor extends AbstractMonitoringInterceptor {

	/**
	 * Create a new PerformanceMonitorInterceptor with a static logger.
	 */
	public PerformanceMonitorForEachMethodInterceptor() {
	}
	
	private AbstractStopWatchSummary stopWatchSummary;
	
	public void setStopWatchSummary(AbstractStopWatchSummary stopWatchSummary) {
		this.stopWatchSummary = stopWatchSummary;
	}

	@Override
	protected Object invokeUnderTrace(MethodInvocation invocation, Logger logger) throws Throwable {
		String name = createInvocationTraceName(invocation);
		StopWatch stopWatch = new StopWatch(name);
		stopWatch.start();
		
		try {
			stopWatch.mark(new StringBuilder(name).append(" Start").toString());
			return invocation.proceed();
		} finally {
			stopWatch.mark(new StringBuilder(name).append(" End").toString());
			stopWatch.stop();
			stopWatchSummary.summarize(stopWatch, logger);
		}
	}

}