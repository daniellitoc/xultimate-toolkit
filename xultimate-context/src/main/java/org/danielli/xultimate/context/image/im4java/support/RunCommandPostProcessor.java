package org.danielli.xultimate.context.image.im4java.support;

import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.im4java.ImageCommandPostProcessor;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;

/**
 * 默认实现。运行命令后处理器。用于执行图片命令。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class RunCommandPostProcessor implements ImageCommandPostProcessor {

	@Override
	public void postProcessAfterInitialization(ImageCommand imageCommand, IMOperation operation, Object... params) throws ImageException {
		try {
			imageCommand.run(operation, params);
		} catch (Exception e) {
			throw new ImageException(e.getMessage(), e);
		} 
	}

}
