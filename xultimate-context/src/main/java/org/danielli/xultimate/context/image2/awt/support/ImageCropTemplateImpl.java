package org.danielli.xultimate.context.image2.awt.support;

import java.awt.image.BufferedImage;

import org.danielli.xultimate.context.image2.AbstractImageTemplate;
import org.danielli.xultimate.context.image2.ImageCropTemplate;
import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.awt.ImageUtils;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.GeometryOperator;
import org.danielli.xultimate.context.image2.config.Gravity;
import org.danielli.xultimate.context.image2.config.ImageCoordinate;
import org.danielli.xultimate.context.image2.config.ImageGeometry;
import org.danielli.xultimate.context.image2.config.ImageGeometryCoordinate;
import org.danielli.xultimate.context.image2.config.ImageInfo;
import org.danielli.xultimate.context.image2.config.ImageSize;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;

/**
 * AWT图片剪裁模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageCropTemplateImpl extends AbstractImageTemplate implements ImageCropTemplate {
	
	@Override
	public void cropImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, ImageSize imageSize, Gravity gravity) throws ImageException {
		BufferedImage srcBufferedImage = srcImageResource.getBufferedImage();
		ImageInfo srcImageInfo = srcImageResource.getImageInfo();
		ImageSize destImageSize = new ImageGeometry(imageSize, GeometryOperator.Emphasize).convertImageSize((ImageSize) srcImageInfo.getImageSize());
		destImageSize.setCrop((ImageSize) srcImageInfo.getImageSize());
		ImageCoordinate coordinate = new ImageCoordinate((ImageSize) srcImageInfo.getImageSize(), destImageSize, gravity);
		BufferedImage destBufferedImage = ImageUtils.cropImage(srcBufferedImage, new ImageGeometryCoordinate(destImageSize, coordinate), backgroundColor);
		if (destImageResource.getImageFile() != null) {
			ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageResource.getImageFile(), quality);
		} else {
			DefaultImageResource defaultImageResource = new DefaultImageResource(destBufferedImage, srcImageInfo.getImageFormat());
			destImageResource.setImageResource(defaultImageResource);
		}
	}
	
	@Override
	public void cropImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, ImageGeometryCoordinate imageGeometryCoordinate) throws ImageException {
		BufferedImage srcBufferedImage = srcImageResource.getBufferedImage();
		ImageInfo srcImageInfo = srcImageResource.getImageInfo();
		ImageSize destImageSize = new ImageGeometry(imageGeometryCoordinate.getImageSize(), GeometryOperator.Emphasize).convertImageSize((ImageSize) srcImageInfo.getImageSize());
		destImageSize.setCrop(srcImageInfo.getImageSize());
		BufferedImage destBufferedImage = ImageUtils.cropImage(srcBufferedImage, new ImageGeometryCoordinate(destImageSize, imageGeometryCoordinate.getImageCoordinate()), backgroundColor);
		if (destImageResource.getImageFile() != null) {
			ImageUtils.writeBufferedImage(destBufferedImage, srcImageInfo.getImageFormat(), destImageResource.getImageFile(), quality);
		} else {
			DefaultImageResource defaultImageResource = new DefaultImageResource(destBufferedImage, srcImageInfo.getImageFormat());
			destImageResource.setImageResource(defaultImageResource);
		}
	}
}
