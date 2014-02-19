package org.danielli.xultimate.context.performance;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;

/**
 * Base {@code MethodInterceptor} implementation for tracing.
 *
 * <p>By default, log messages are written to the log for the interceptor class,
 * not the class which is being intercepted. Setting the {@code useDynamicLogger}
 * bean property to {@code true} causes all log messages to be written to
 * the {@code Logger} for the target class being intercepted.
 *
 * <p>Subclasses must implement the {@code invokeUnderTrace} method, which
 * is invoked by this class ONLY when a particular invocation SHOULD be traced.
 * Subclasses should write to the {@code Logger} instance provided.
 *
 * @author Rob Harrop
 * @author Juergen Hoeller
 * @since 1.2
 * @see #setUseDynamicLogger
 * @see #invokeUnderTrace(org.aopalliance.intercept.MethodInvocation, Logger)
 */
public abstract class AbstractTraceInterceptor implements MethodInterceptor {

	/**
	 * The default {@code Logger} instance used to write trace messages.
	 * This instance is mapped to the implementing {@code Class}.
	 */
	protected transient Logger defaultLogger = LoggerFactory.getLogger(getClass());

	/**
	 * Indicates whether or not proxy class names should be hidden when using dynamic loggers.
	 * @see #setUseDynamicLogger
	 */
	private boolean hideProxyClassNames = false;


	/**
	 * Set whether to use a dynamic logger or a static logger.
	 * Default is a static logger for this trace interceptor.
	 * <p>Used to determine which {@code Logger} instance should be used to write
	 * log messages for a particular method invocation: a dynamic one for the
	 * {@code Class} getting called, or a static one for the {@code Class}
	 * of the trace interceptor.
	 * <p><b>NOTE:</b> Specify either this property or "loggerName", not both.
	 * @see #getLoggerForInvocation(org.aopalliance.intercept.MethodInvocation)
	 */
	public void setUseDynamicLogger(boolean useDynamicLogger) {
		// Release default logger if it is not being used.
		this.defaultLogger = (useDynamicLogger ? null : LoggerFactory.getLogger(getClass()));
	}

	/**
	 * Set the name of the logger to use. The name will be passed to the
	 * underlying logger implementation through SLF4j, getting
	 * interpreted as log category according to the logger's configuration.
	 * <p>This can be specified to not log into the category of a class
	 * (whether this interceptor's class or the class getting called)
	 * but rather into a specific named category.
	 * <p><b>NOTE:</b> Specify either this property or "useDynamicLogger", not both.
	 * @see Logger#getLogger(String)
	 */
	public void setLoggerName(String loggerName) {
		this.defaultLogger = LoggerFactory.getLogger(loggerName);
	}

	/**
	 * Set to "true" to have {@link #setUseDynamicLogger dynamic loggers} hide
	 * proxy class names wherever possible. Default is "false".
	 */
	public void setHideProxyClassNames(boolean hideProxyClassNames) {
		this.hideProxyClassNames = hideProxyClassNames;
	}


	/**
	 * Determines whether or not logging is enabled for the particular {@code MethodInvocation}.
	 * If not, the method invocation proceeds as normal, otherwise the method invocation is passed
	 * to the {@code invokeUnderTrace} method for handling.
	 * @see #invokeUnderTrace(org.aopalliance.intercept.MethodInvocation, Logger)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Logger logger = getLoggerForInvocation(invocation);
		if (isInterceptorEnabled(invocation, logger)) {
			return invokeUnderTrace(invocation, logger);
		}
		else {
			return invocation.proceed();
		}
	}

	/**
	 * Return the appropriate {@code Logger} instance to use for the given
	 * {@code MethodInvocation}. If the {@code useDynamicLogger} flag
	 * is set, the {@code Logger} instance will be for the target class of the
	 * {@code MethodInvocation}, otherwise the {@code Logger} will be the
	 * default static logger.
	 * @param invocation the {@code MethodInvocation} being traced
	 * @return the {@code Logger} instance to use
	 * @see #setUseDynamicLogger
	 */
	protected Logger getLoggerForInvocation(MethodInvocation invocation) {
		if (this.defaultLogger != null) {
			return this.defaultLogger;
		}
		else {
			Object target = invocation.getThis();
			return LoggerFactory.getLogger(getClassForLogging(target));
		}
	}

	/**
	 * Determine the class to use for logging purposes.
	 * @param target the target object to introspect
	 * @return the target class for the given object
	 * @see #setHideProxyClassNames
	 */
	protected Class<?> getClassForLogging(Object target) {
		return (this.hideProxyClassNames ? AopUtils.getTargetClass(target) : target.getClass());
	}

	/**
	 * Determine whether the interceptor should kick in, that is,
	 * whether the {@code invokeUnderTrace} method should be called.
	 * <p>Default behavior is to check whether the given {@code Logger}
	 * instance is enabled. Subclasses can override this to apply the
	 * interceptor in other cases as well.
	 * @param invocation the {@code MethodInvocation} being traced
	 * @param logger the {@code Logger} instance to check
	 * @see #invokeUnderTrace
	 * @see #isLoggerEnabled
	 */
	protected boolean isInterceptorEnabled(MethodInvocation invocation, Logger logger) {
		return isLoggerEnabled(logger);
	}

	/**
	 * Determine whether the given {@link Logger} instance is enabled.
	 * <p>Default is {@code true} when the "trace" level is enabled.
	 * Subclasses can override this to change the level under which 'tracing' occurs.
	 * @param logger the {@code Logger} instance to check
	 */
	protected boolean isLoggerEnabled(Logger logger) {
		return logger.isTraceEnabled();
	}


	/**
	 * Subclasses must override this method to perform any tracing around the
	 * supplied {@code MethodInvocation}. Subclasses are responsible for
	 * ensuring that the {@code MethodInvocation} actually executes by
	 * calling {@code MethodInvocation.proceed()}.
	 * <p>By default, the passed-in {@code Logger} instance will have log level
	 * "trace" enabled. Subclasses do not have to check for this again, unless
	 * they overwrite the {@code isInterceptorEnabled} method to modify
	 * the default behavior.
	 * @param logger the {@code Logger} to write trace messages to
	 * @return the result of the call to {@code MethodInvocation.proceed()}
	 * @throws Throwable if the call to {@code MethodInvocation.proceed()}
	 * encountered any errors
	 * @see #isInterceptorEnabled
	 * @see #isLoggerEnabled
	 */
	protected abstract Object invokeUnderTrace(MethodInvocation invocation, Logger logger) throws Throwable;
}
