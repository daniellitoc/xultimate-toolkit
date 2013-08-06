package org.danielli.xultimate.web.context.response;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * Gzip响应流。
 *
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class GzipResponseStream extends ServletOutputStream {
	
	private OutputStream outputStream;

    public GzipResponseStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(int b) throws IOException {
    	outputStream.write(b);
    }

    public void write(byte[] b) throws IOException {
    	outputStream.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
    	outputStream.write(b, off, len);
    }

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener arg0) {
	}
}