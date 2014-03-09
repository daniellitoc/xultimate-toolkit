package org.danielli.xultimate.context.image2.im4java;

import org.danielli.xultimate.context.image2.AbstractImageTemplate;
import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;
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
	
	private ImageCommandPostProcessor[] imageCommandPostProcessors;

	public void setImageCommandPostProcessors(ImageCommandPostProcessor[] imageCommandPostProcessors) {
		this.imageCommandPostProcessors = imageCommandPostProcessors;
	}

	protected void runOperation(ImageCommand imageCommand, IMOperation operation, WrapperImageResource destImageResource, DefaultImageResource... parameterImageResources) throws ImageException {
		for (ImageCommandPostProcessor imageCommandPostProcessor : imageCommandPostProcessors) {
			imageCommandPostProcessor.postProcessAfterInitialization(imageCommand, operation, destImageResource, parameterImageResources);
		}
	}

	public void setUseGraphicsMagick(boolean useGraphicsMagick) {
		this.useGraphicsMagick = useGraphicsMagick;
	}
}
