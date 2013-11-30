package org.danielli.xultimate.context.image.im4java;

import org.danielli.xultimate.context.image.AbstractImageTemplate;
import org.danielli.xultimate.context.image.ImageException;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;

/**
 * Im4java抽像图片模板工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public abstract class AbstractIm4javaImageTemplate extends AbstractImageTemplate {
	
	protected boolean useGraphicsMagick = true;
	
	private ImageCommandPostProcessor imageCommandPostProcessor;

	public void setImageCommandPostProcessor(ImageCommandPostProcessor imageCommandPostProcessor) {
		this.imageCommandPostProcessor = imageCommandPostProcessor;
	}

	protected void runOperation(IMOperation operation, Object... params) throws ImageException {
		imageCommandPostProcessor.postProcessAfterInitialization(createImageCommand(operation, params), operation, params);
	}

	public void setUseGraphicsMagick(boolean useGraphicsMagick) {
		this.useGraphicsMagick = useGraphicsMagick;
	}
	
	public abstract ImageCommand createImageCommand(IMOperation operation, Object... params); 
}
