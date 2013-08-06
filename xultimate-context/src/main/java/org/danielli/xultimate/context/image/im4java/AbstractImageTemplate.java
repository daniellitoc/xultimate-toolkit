package org.danielli.xultimate.context.image.im4java;

import org.danielli.xultimate.context.image.ImageException;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;

/**
 * Im4java抽像图片模板工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public abstract class AbstractImageTemplate {
	
	protected ImageCommand imageCommand;
	
	protected Integer quality = 100;
	
	protected void runOperation(IMOperation operation) throws ImageException {
		try {
			imageCommand.createScript("/home/toc/Pictures/a.sh", operation);
			imageCommand.run(operation);
		} catch (Exception e) {
			throw new ImageException(e.getMessage(), e);
		} 
	}
	

	public void setImageCommand(ImageCommand imageCommand) {
		this.imageCommand = imageCommand;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}
}
