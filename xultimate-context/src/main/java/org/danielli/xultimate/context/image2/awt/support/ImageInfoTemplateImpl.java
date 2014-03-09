package org.danielli.xultimate.context.image2.awt.support;

import java.awt.image.BufferedImage;

import org.danielli.xultimate.context.image2.AbstractImageTemplate;
import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.ImageInfoTemplate;
import org.danielli.xultimate.context.image2.awt.ImageUtils;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.ImageInfo;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;

/**
 * AWT图片信息模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageInfoTemplateImpl extends AbstractImageTemplate implements ImageInfoTemplate {

	@Override
	public void convertImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource) throws ImageException {
		BufferedImage srcBufferedImage = srcImageResource.getBufferedImage();
		ImageInfo srcImageInfo = srcImageResource.getImageInfo();
		if (destImageResource.getImageFile() != null) {
			ImageUtils.writeBufferedImage(srcBufferedImage, srcImageInfo.getImageFormat(), destImageResource.getImageFile(), quality);
		} else {
			DefaultImageResource defaultImageResource = new DefaultImageResource(srcBufferedImage, srcImageInfo.getImageFormat());
			destImageResource.setImageResource(defaultImageResource);
		}
	}
}
