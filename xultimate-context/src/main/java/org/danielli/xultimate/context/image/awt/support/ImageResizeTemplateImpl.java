package org.danielli.xultimate.context.image.awt.support;

import java.awt.image.BufferedImage;
import java.io.File;

import org.danielli.xultimate.context.image.AbstractImageTemplate;
import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.ImageResizeTemplate;
import org.danielli.xultimate.context.image.awt.ImageUtils;
import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageGeometry;
import org.danielli.xultimate.context.image.model.ImageInfo;

/**
 * AWT图片缩放模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageResizeTemplateImpl extends AbstractImageTemplate implements ImageResizeTemplate {

	@Override
	public void resizeImage(File srcImageFile, File destImageFile, ImageGeometry imageGeometry) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		BufferedImage destBufferedImage = ImageUtils.resizeImage(srcBufferedImage, imageGeometry, backgroundColor);
		ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageFile, quality);
		
	}
	
	@Override
	public void resizeImageAsFixed(File srcImageFile, File destImageFile, ImageGeometry imageGeometry, Gravity gravity) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		BufferedImage destBufferedImage = ImageUtils.resizeImage(srcBufferedImage, imageGeometry, gravity, backgroundColor);
		ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageFile, quality);
	}
}
