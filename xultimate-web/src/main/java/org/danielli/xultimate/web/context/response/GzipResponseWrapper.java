package org.danielli.xultimate.web.context.response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Gzip响应包装器。
 *
 * @author Daniel Li
 * @since 15 Jun 2013
 *
 */
public class GzipResponseWrapper extends HttpServletResponseWrapper implements Serializable {
	
	private static final long serialVersionUID = 8367490736472222429L;
	
	private int statusCode = SC_OK;
	private ServletOutputStream servletOutputStream;
	private PrintWriter printWriter;

	public GzipResponseWrapper(HttpServletResponse response, OutputStream outputStream) {
		super(response);
		this.servletOutputStream = new GzipResponseStream(outputStream);
	}
	
	public ServletOutputStream getOutputStream() {
		return servletOutputStream;
	}
	
	public void setStatus(int code) {
		statusCode = code;
		super.setStatus(code);
	}
	
	public void setStatus(int code, String msg) {
		statusCode = code;
		super.setStatus(code);
	}
	
	public int getStatus() {
		return statusCode;
	}
	
	public void sendError(int i) throws IOException {
		statusCode = i;
		super.sendError(i);
	}
	
	public void sendError(int i, String string) throws IOException {
		statusCode = i;
		super.sendError(i, string);
	}
	
	public void sendRedirect(String string) throws IOException {
		statusCode = HttpServletResponse.SC_MOVED_TEMPORARILY;
		super.sendRedirect(string);
	}
	
	public void reset() {
		super.reset();
		statusCode = SC_OK;
	}
    
	public PrintWriter getWriter() throws IOException {
		if (printWriter == null) {
			printWriter = new PrintWriter(new OutputStreamWriter(servletOutputStream, getCharacterEncoding()), true);
		}
		return printWriter;
	}

	public void flushBuffer() throws IOException {
		flush();
	}
    
	public void flush() throws IOException {
		if (printWriter != null) {
			printWriter.flush();
		}
		servletOutputStream.flush();
	}

}
