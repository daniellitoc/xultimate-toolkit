package org.danielli.xultimate.web.servlet.i18n;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Servlet 2.4+ listener that exposes the request to the current thread,
 * through both {@link org.springframework.context.i18n.LocaleContextHolder} and
 * {@link RequestContextHolder}. To be registered as listener in {@code web.xml}.
 *
 * <p>Alternatively, Spring's {@link org.springframework.web.filter.RequestContextFilter}
 * and Spring's {@link org.springframework.web.servlet.DispatcherServlet} also expose
 * the same request context to the current thread. In contrast to this listener,
 * advanced options are available there (e.g. "threadContextInheritable").
 *
 * <p>This listener is mainly for use with third-party servlets, e.g. the JSF FacesServlet.
 * Within Spring's own web support, DispatcherServlet's processing is perfectly sufficient.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see javax.servlet.ServletRequestListener
 * @see org.springframework.context.i18n.LocaleContextHolder
 * @see RequestContextHolder
 * @see org.springframework.web.filter.RequestContextFilter
 * @see org.springframework.web.servlet.DispatcherServlet
 */
public class RequestContextInterceptor extends HandlerInterceptorAdapter {

	private static final String REQUEST_ATTRIBUTES_ATTRIBUTE = RequestContextListener.class.getName() + ".REQUEST_ATTRIBUTES";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ServletRequestAttributes attributes = new ServletRequestAttributes(request);
		request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);
		LocaleContextHolder.setLocale(RequestContextUtils.getLocale(request));
		RequestContextHolder.setRequestAttributes(attributes);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		ServletRequestAttributes attributes = (ServletRequestAttributes) request.getAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE);
		ServletRequestAttributes threadAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (threadAttributes != null) {
			// We're assumably within the original request thread...
			if (attributes == null) {
				attributes = threadAttributes;
			}
			LocaleContextHolder.resetLocaleContext();
			RequestContextHolder.resetRequestAttributes();
		}
		if (attributes != null) {
			attributes.requestCompleted();
		}
	}
}
