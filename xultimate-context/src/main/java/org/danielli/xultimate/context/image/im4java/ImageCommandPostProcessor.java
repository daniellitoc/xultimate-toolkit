package org.danielli.xultimate.context.image.im4java;

import org.danielli.xultimate.context.image.ImageException;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;

/**
 * 图片命令后处理器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface ImageCommandPostProcessor {
	
	/**
	 * 初始化图片命令和操作后，此方法被执行。
	 */
	void postProcessAfterInitialization(ImageCommand imageCommand, IMOperation operation, Object... params) throws ImageException;
}
