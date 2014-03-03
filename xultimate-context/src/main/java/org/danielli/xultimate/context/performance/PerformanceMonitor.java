package org.danielli.xultimate.context.performance;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 性能检测注解。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see PerformanceMonitorForEachMethodInterceptor
 * @see PerformanceMonitorForOneMethodInterceptor
 */
@Inherited
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PerformanceMonitor {

}
