package org.danielli.xultimate.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.zip.GZIPOutputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.web.context.response.GzipResponseWrapper;
import org.danielli.xultimate.web.util.WebUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Gzip压缩过滤器。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class GzipCompressFilter extends GenericFilterBean {
	
	private static final int EMPTY_GZIPPED_CONTENT_SIZE = 20;
	
	private Boolean isGzipEnabled = true;

    private boolean setVaryHeader;

	public void setIsGzipEnabled(Boolean isGzipEnabled) {
		this.isGzipEnabled = isGzipEnabled;
	}
    
	public void setSetVaryHeader(boolean setVaryHeader) {
		this.setVaryHeader = setVaryHeader;
	}
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (isGzipEnabled && !isIncluded(request) && acceptsGzipEncoding(request) && !response.isCommitted()) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            GzipResponseWrapper gzipResponseWrapper = new GzipResponseWrapper(response, gZIPOutputStream);
            chain.doFilter(request, gzipResponseWrapper);
            gzipResponseWrapper.flush();
            gZIPOutputStream.close();
            
            if (gzipResponseWrapper.getStatus() != HttpServletResponse.SC_OK) {
                return;
            }
            
            byte[] byteArrayOutputStreamBytes = byteArrayOutputStream.toByteArray();
            if (byteArrayOutputStreamBytes.length == EMPTY_GZIPPED_CONTENT_SIZE) {
            	byteArrayOutputStreamBytes = new byte[0];
            }
            
            WebUtils.addHeader(response, WebUtils.CONTENT_ENCODING, "gzip");
            
            if (setVaryHeader) {
            	WebUtils.addHeader(response, WebUtils.VARY, WebUtils.ACCEPT_ENCODING);
            }
            
            response.setContentLength(byteArrayOutputStreamBytes.length);
            response.getOutputStream().write(byteArrayOutputStreamBytes);
        } else {
            chain.doFilter(request, response);
        }
	}
	
    protected boolean acceptsGzipEncoding(HttpServletRequest request) {
    	Iterator<String> iterator = WebUtils.getAcceptEncoding(request);
    	while (iterator.hasNext()) {
    		if (StringUtils.equalsIgnoreCase("gzip", iterator.next()))
        		return true;
    	}
        return false;
    }
    
    private boolean isIncluded(final HttpServletRequest request) {
        final boolean includeRequest = WebUtils.isIncludeRequest(request);
        return includeRequest;
    }
}
