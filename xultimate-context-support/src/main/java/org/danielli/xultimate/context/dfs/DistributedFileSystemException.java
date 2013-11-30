package org.danielli.xultimate.context.dfs;

import org.springframework.core.NestedRuntimeException;

public class DistributedFileSystemException extends NestedRuntimeException {
	
	private static final long serialVersionUID = -5363328862858087052L;

	public DistributedFileSystemException(String message) {
		super(message);
	}
	
	public DistributedFileSystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
