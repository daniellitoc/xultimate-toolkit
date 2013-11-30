package org.danielli.xultimate.context.image.awt.support;

import java.awt.image.BufferedImage;
import java.io.File;

import org.danielli.xultimate.context.image.AbstractImageTemplate;
import org.danielli.xultimate.context.image.ImageCropTemplate;
import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.awt.ImageUtils;
import org.danielli.xultimate.context.image.model.GeometryOperator;
import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageCoordinate;
import org.danielli.xultimate.context.image.model.ImageGeometry;
import org.danielli.xultimate.context.image.model.ImageGeometryCoordinate;
import org.danielli.xultimate.context.image.model.ImageInfo;
import org.danielli.xultimate.context.image.model.ImageSize;

/**
 * AWT图片剪裁模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageCropTemplateImpl extends AbstractImageTemplate implements ImageCropTemplate {

	@Override
	public void cropImage(File srcImageFile, File destImageFile, ImageSize imageSize, Gravity gravity) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		ImageSize destImageSize = new ImageGeometry(imageSize, GeometryOperator.Emphasize).convertImageSize((ImageSize) srcImageInfo.getImageSize());
		destImageSize.setCrop((ImageSize) srcImageInfo.getImageSize());
		ImageCoordinate coordinate = new ImageCoordinate((ImageSize) srcImageInfo.getImageSize(), destImageSize, gravity);
		BufferedImage destBufferedImage = ImageUtils.cropImage(srcBufferedImage, new ImageGeometryCoordinate(destImageSize, coordinate), backgroundColor);
		ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageFile, quality);
		
	}
	
	@Override
	public void cropImage(File srcImageFile, File destImageFile, ImageGeometryCoordinate imageGeometryCoordinate) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		ImageSize destImageSize = new ImageGeometry(imageGeometryCoordinate.getImageSize(), GeometryOperator.Emphasize).convertImageSize((ImageSize) srcImageInfo.getImageSize());
		destImageSize.setCrop((ImageSize) srcImageInfo.getImageSize());
		BufferedImage destBufferedImage = ImageUtils.cropImage(srcBufferedImage, new ImageGeometryCoordinate(destImageSize, imageGeometryCoordinate.getImageCoordinate()), backgroundColor);
		ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageFile, quality);
	}
}
