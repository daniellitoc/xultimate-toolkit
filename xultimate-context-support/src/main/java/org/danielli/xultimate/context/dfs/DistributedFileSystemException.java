package org.danielli.xultimate.context.dfs;

import org.springframework.core.NestedRuntimeException;

/**
 * 分布式文件系统异常。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 * @see NestedRuntimeException
 */
public class DistributedFileSystemException extends NestedRuntimeException {
	
	private static final long serialVersionUID = -5363328862858087052L;

	public DistributedFileSystemException(String message) {
		super(message);
	}
	
	public DistributedFileSystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
