package org.danielli.xultimate.context.image.awt.support;

import java.awt.image.BufferedImage;
import java.io.File;

import org.danielli.xultimate.context.image.ImageCropTemplate;
import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.awt.ImageUtils;
import org.danielli.xultimate.context.image.model.GeometryOperator;
import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageCoordinate;
import org.danielli.xultimate.context.image.model.ImageGeometry;
import org.danielli.xultimate.context.image.model.ImageInfo;
import org.danielli.xultimate.context.image.model.ImageSize;

/**
 * AWT图片剪裁模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageCropTemplateImpl implements ImageCropTemplate {

	@Override
	public void cropImage(File srcImageFile, File destImageFile, ImageSize imageSize, Gravity gravity) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		ImageSize destImageSize = new ImageGeometry(imageSize, GeometryOperator.Emphasize).convertImageSize((ImageSize) srcImageInfo.getImageSize());
		destImageSize.setCrop((ImageSize) srcImageInfo.getImageSize());
		ImageCoordinate coordinate = new ImageCoordinate((ImageSize) srcImageInfo.getImageSize(), destImageSize, gravity);
		BufferedImage destBufferedImage = ImageUtils.cropImage(srcBufferedImage, destImageSize, coordinate);
		ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageFile);
		
	}

	@Override
	public void cropImage(File srcImageFile, File destImageFile, ImageSize imageSize, ImageCoordinate imageCoordinate) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		ImageSize destImageSize = new ImageGeometry(imageSize, GeometryOperator.Emphasize).convertImageSize((ImageSize) srcImageInfo.getImageSize());
		destImageSize.setCrop((ImageSize) srcImageInfo.getImageSize());
		BufferedImage destBufferedImage = ImageUtils.cropImage(srcBufferedImage, destImageSize, imageCoordinate);
		ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageFile);
	}

}
