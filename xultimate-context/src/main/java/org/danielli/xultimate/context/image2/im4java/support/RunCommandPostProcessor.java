package org.danielli.xultimate.context.image2.im4java.support;

import java.util.ArrayList;
import java.util.List;

import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.ImageFormat;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;
import org.danielli.xultimate.context.image2.im4java.ImageCommandPostProcessor;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;
import org.im4java.core.Stream2BufferedImage;

/**
 * 默认实现。运行命令后处理器。用于执行图片命令。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class RunCommandPostProcessor implements ImageCommandPostProcessor {

	@Override
	public void postProcessAfterInitialization(ImageCommand imageCommand, IMOperation operation, WrapperImageResource destImageResource, DefaultImageResource... parameterImageResources) throws ImageException {
		try {
			List<Object> parameters = new ArrayList<>(parameterImageResources.length);
			for (DefaultImageResource imageResource : parameterImageResources) {
				if (imageResource != null) {
					if (imageResource.getImageFile() == null) {
						parameters.add(imageResource.getBufferedImage());
					} else {
						parameters.add(imageResource.getImageFile().getPath());
					}
				}
			}
			if (destImageResource.getImageFile() == null) {
				ImageFormat destImageFormat = parameterImageResources[parameterImageResources.length - 1].getImageInfo().getImageFormat();
				operation.addImage(destImageFormat.name() + ":-");
				Stream2BufferedImage stream2BufferedImage = new Stream2BufferedImage();
				imageCommand.setOutputConsumer(stream2BufferedImage);
				imageCommand.run(operation, parameters.toArray());
				DefaultImageResource imageResource = new DefaultImageResource(stream2BufferedImage.getImage(), destImageFormat);
				destImageResource.setImageResource(imageResource);
			} else {
				operation.addImage(destImageResource.getImageFile().getPath());
				imageCommand.run(operation, parameters.toArray());
			}
		} catch (Exception e) {
			throw new ImageException(e.getMessage(), e);
		} 
	}
}
