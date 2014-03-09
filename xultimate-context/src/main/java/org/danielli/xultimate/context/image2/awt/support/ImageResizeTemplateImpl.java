package org.danielli.xultimate.context.image2.awt.support;

import java.awt.image.BufferedImage;

import org.danielli.xultimate.context.image2.AbstractImageTemplate;
import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.ImageResizeTemplate;
import org.danielli.xultimate.context.image2.awt.ImageUtils;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.Gravity;
import org.danielli.xultimate.context.image2.config.ImageGeometry;
import org.danielli.xultimate.context.image2.config.ImageInfo;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;

/**
 * AWT图片缩放模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageResizeTemplateImpl extends AbstractImageTemplate implements ImageResizeTemplate {

	@Override
	public void resizeImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, ImageGeometry imageGeometry) throws ImageException {
		BufferedImage srcBufferedImage = srcImageResource.getBufferedImage();
		ImageInfo srcImageInfo = srcImageResource.getImageInfo();
		BufferedImage destBufferedImage = ImageUtils.resizeImage(srcBufferedImage, imageGeometry, backgroundColor);
		if (destImageResource.getImageFile() != null) {
			ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageResource.getImageFile(), quality);
		} else {
			DefaultImageResource defaultImageResource = new DefaultImageResource(destBufferedImage, srcImageInfo.getImageFormat());
			destImageResource.setImageResource(defaultImageResource);
		}
	}
	
	@Override
	public void resizeImageAsFixed(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, ImageGeometry imageGeometry, Gravity gravity) throws ImageException {
		BufferedImage srcBufferedImage = srcImageResource.getBufferedImage();
		ImageInfo srcImageInfo = srcImageResource.getImageInfo();
		BufferedImage destBufferedImage = ImageUtils.resizeImage(srcBufferedImage, imageGeometry, gravity, backgroundColor);
		if (destImageResource.getImageFile() != null) {
			ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageResource.getImageFile(), quality);
		} else {
			DefaultImageResource defaultImageResource = new DefaultImageResource(destBufferedImage, srcImageInfo.getImageFormat());
			destImageResource.setImageResource(defaultImageResource);
		}
	}
}
