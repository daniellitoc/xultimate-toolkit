package org.danielli.xultimate.context.chardet.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.danielli.xultimate.context.chardet.CharsetDetectorException;
import org.danielli.xultimate.util.io.FileUtils;

/**
 * 文件类字符集检测器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see AbstractCharsetDetector
 */
public class FileCharsetDetector extends AbstractCharsetDetector<File> {
	
	@Override
	public InputStream createInputStream(File source) throws CharsetDetectorException {
		try {
			return FileUtils.openInputStream(source);
		} catch (IOException e) {
			throw new CharsetDetectorException(e.getMessage(), e);
		} 
	}
}
