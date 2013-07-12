package org.danielli.xultimate.util.builder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 构建标识，用于表示是否支持ToString、HashCode、Equals、CompareTo。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Buildable {
	
	BuildType[] value() default {};
	
}
