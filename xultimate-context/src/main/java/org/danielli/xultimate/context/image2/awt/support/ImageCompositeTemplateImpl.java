package org.danielli.xultimate.context.image2.awt.support;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import org.apache.commons.lang3.StringUtils;
import org.danielli.xultimate.context.image2.AbstractImageTemplate;
import org.danielli.xultimate.context.image2.ImageCompositeTemplate;
import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.awt.ImageUtils;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.Gravity;
import org.danielli.xultimate.context.image2.config.ImageCoordinate;
import org.danielli.xultimate.context.image2.config.ImageInfo;
import org.danielli.xultimate.context.image2.config.ImageSize;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;
import org.danielli.xultimate.util.Assert;

/**
 * AWT图片组合模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageCompositeTemplateImpl extends AbstractImageTemplate implements ImageCompositeTemplate {

	/** 水印图片透明度 */
	private Integer alpha = 100;
	
	/**
	 * 设置水印图片透明度
	 * @param alpha
	 * 				水印图片透明度
	 */
	public void setAlpha(Integer alpha) {
		this.alpha = alpha;
	}
	
	@Override
	public void addWatermarkImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, DefaultImageResource watermarkImageResource, Gravity gravity) throws ImageException {
		BufferedImage srcBufferedImage = srcImageResource.getBufferedImage();
		ImageInfo srcImageInfo = srcImageResource.getImageInfo();
		if (watermarkImageResource != null) {
			Assert.notNull(gravity, "this gravity is required; it must not be null");
			if (gravity != Gravity.None) {
				BufferedImage watermarkBufferedImage = watermarkImageResource.getBufferedImage();
				ImageCoordinate imageCoordinate = new ImageCoordinate(srcImageInfo.getImageSize(), new ImageSize(watermarkBufferedImage), gravity);
				srcBufferedImage = ImageUtils.addWatermarkImage(srcBufferedImage, watermarkBufferedImage, imageCoordinate, alpha, backgroundColor);
			}
		}
		if (destImageResource.getImageFile() != null) {
			ImageUtils.writeBufferedImage(srcBufferedImage, srcImageInfo.getImageFormat(), destImageResource.getImageFile(), quality);
		} else {
			DefaultImageResource defaultImageResource = new DefaultImageResource(srcBufferedImage, srcImageInfo.getImageFormat());
			destImageResource.setImageResource(defaultImageResource);
		}
	}
	
	@Override
	public void addWatermarkImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, DefaultImageResource watermarkImageResource, ImageCoordinate imageCoordinate) throws ImageException {
		BufferedImage srcBufferedImage = srcImageResource.getBufferedImage();
		ImageInfo srcImageInfo = srcImageResource.getImageInfo();
		if (watermarkImageResource != null) {
			Assert.notNull(imageCoordinate, "this imageCoordinate is required; it must not be null");
			BufferedImage watermarkBufferedImage = watermarkImageResource.getBufferedImage();
			srcBufferedImage = ImageUtils.addWatermarkImage(srcBufferedImage, watermarkBufferedImage, imageCoordinate, alpha, backgroundColor);
		}
		if (destImageResource.getImageFile() != null) {
			ImageUtils.writeBufferedImage(srcBufferedImage, srcImageInfo.getImageFormat(), destImageResource.getImageFile(), quality);
		} else {
			DefaultImageResource defaultImageResource = new DefaultImageResource(srcBufferedImage, srcImageInfo.getImageFormat());
			destImageResource.setImageResource(defaultImageResource);
		}
	}
	
	@Override
	public void addWatermarkText(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, String watermarkText, Font watermarkTextFont, Color watermarkTextColor, Gravity gravity) throws ImageException {
		BufferedImage srcBufferedImage = srcImageResource.getBufferedImage();
		ImageInfo srcImageInfo = srcImageResource.getImageInfo();
		if (StringUtils.isNotEmpty(watermarkText)) {
			Assert.notNull(gravity, "this gravity is required; it must not be null");
			if (gravity != Gravity.None) {
				ImageSize watermarkImageSize = ImageUtils.getFontAsImageWidth(watermarkText, watermarkTextFont);
				BufferedImage watermarkBufferedImage = ImageUtils.createTransluentBufferedImage(watermarkImageSize, watermarkText, watermarkTextFont, watermarkTextColor);
				ImageCoordinate imageCoordinate = new ImageCoordinate(new ImageSize(srcBufferedImage), watermarkImageSize, gravity);
				srcBufferedImage = ImageUtils.addWatermarkImage(srcBufferedImage, watermarkBufferedImage, imageCoordinate, alpha, backgroundColor);
			}
		}
		if (destImageResource.getImageFile() != null) {
			ImageUtils.writeBufferedImage(srcBufferedImage, srcImageInfo.getImageFormat(), destImageResource.getImageFile(), quality);
		} else {
			DefaultImageResource defaultImageResource = new DefaultImageResource(srcBufferedImage, srcImageInfo.getImageFormat());
			destImageResource.setImageResource(defaultImageResource);
		}
	}

	@Override
	public void addWatermarkText(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, String watermarkText, Font watermarkTextFont, Color watermarkTextColor, ImageCoordinate imageCoordinate) throws ImageException {
		BufferedImage srcBufferedImage = srcImageResource.getBufferedImage();
		ImageInfo srcImageInfo = srcImageResource.getImageInfo();
		if (StringUtils.isNotEmpty(watermarkText)) {
			Assert.notNull(imageCoordinate, "this imageCoordinate is required; it must not be null");
			ImageSize watermarkImageSize = ImageUtils.getFontAsImageWidth(watermarkText, watermarkTextFont);
			BufferedImage watermarkBufferedImage = ImageUtils.createTransluentBufferedImage(watermarkImageSize, watermarkText, watermarkTextFont, watermarkTextColor);
			
			srcBufferedImage = ImageUtils.addWatermarkImage(srcBufferedImage, watermarkBufferedImage, imageCoordinate, alpha, backgroundColor);
		}
		if (destImageResource.getImageFile() != null) {
			ImageUtils.writeBufferedImage(srcBufferedImage, srcImageInfo.getImageFormat(), destImageResource.getImageFile(), quality);
		} else {
			DefaultImageResource defaultImageResource = new DefaultImageResource(srcBufferedImage, srcImageInfo.getImageFormat());
			destImageResource.setImageResource(defaultImageResource);
		}
	}
}
