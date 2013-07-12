package org.danielli.xultimate.util.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class AnnotationUtils {
	
	/**
	 * Retrieve the <em>value</em> of the {@code &quot;value&quot;} attribute of a
	 * single-element Annotation, given an annotation instance.
	 * @param annotation the annotation instance from which to retrieve the value
	 * @return the attribute value, or {@code null} if not found
	 * @see #getValue(Annotation, String)
	 */
	public static Object getValue(Annotation annotation) {
		return org.springframework.core.annotation.AnnotationUtils.getValue(annotation);
	}
	
	/**
	 * Get a single {@link Annotation} of {@code annotationType} from the supplied
	 * Method, Constructor or Field. Meta-annotations will be searched if the annotation
	 * is not declared locally on the supplied element.
	 * @param ae the Method, Constructor or Field from which to get the annotation
	 * @param annotationType the annotation class to look for, both locally and as a meta-annotation
	 * @return the matching annotation or {@code null} if not found
	 * @since 3.1
	 */
	public static <T extends Annotation> T getAnnotation(AnnotatedElement ae, Class<T> annotationType) {
		return org.springframework.core.annotation.AnnotationUtils.getAnnotation(ae, annotationType);
	}
}
