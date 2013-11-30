package org.danielli.xultimate.context.image.awt.support;

import java.awt.image.BufferedImage;
import java.io.File;

import org.danielli.xultimate.context.image.AbstractImageTemplate;
import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.ImageInfoException;
import org.danielli.xultimate.context.image.ImageInfoTemplate;
import org.danielli.xultimate.context.image.awt.ImageUtils;
import org.danielli.xultimate.context.image.model.ImageInfo;

/**
 * AWT图片信息模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageInfoTemplateImpl extends AbstractImageTemplate implements ImageInfoTemplate {

	@Override
	public ImageInfo getImageInfo(File imageFile) throws ImageInfoException {
		return ImageUtils.getImageInfo(imageFile);
	}

	@Override
	public void convertImage(File srcImageFile, File destImageFile) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		ImageUtils.writeBufferedImage(srcBufferedImage, srcImageInfo.getImageFormat(), destImageFile, quality);
	}

}
