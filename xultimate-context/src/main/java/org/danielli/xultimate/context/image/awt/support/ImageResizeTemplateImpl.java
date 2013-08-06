package org.danielli.xultimate.context.image.awt.support;

import java.awt.image.BufferedImage;
import java.io.File;

import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.ImageResizeTemplate;
import org.danielli.xultimate.context.image.awt.ImageUtils;
import org.danielli.xultimate.context.image.model.GeometryOperator;
import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageInfo;
import org.danielli.xultimate.context.image.model.ImageSize;

/**
 * AWT图片缩放模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageResizeTemplateImpl implements ImageResizeTemplate {

	@Override
	public void resizeImageAsFixed(File srcImageFile, File destImageFile, ImageSize imageSize) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		BufferedImage destBufferedImage = ImageUtils.resizeImage(srcBufferedImage, imageSize, GeometryOperator.Emphasize);
		ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageFile);
	}
	
	@Override
	public void resizeImageAsAutomatic(File srcImageFile, File destImageFile, ImageSize imageSize) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		BufferedImage destBufferedImage = ImageUtils.resizeImage(srcBufferedImage, imageSize, GeometryOperator.Maximum);
		ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageFile);
	}

	@Override
	public void resizeImageAsAutomatic(File srcImageFile, File destImageFile, ImageSize imageSize, Gravity gravity) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		BufferedImage destBufferedImage = ImageUtils.resizeImage(srcBufferedImage, imageSize, gravity);
		ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageFile);
	}
}
