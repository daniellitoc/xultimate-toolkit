package org.danielli.xultimate.context.image.awt.support;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.danielli.xultimate.context.image.AbstractImageTemplate;
import org.danielli.xultimate.context.image.ImageCompositeTemplate;
import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.awt.ImageUtils;
import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageCoordinate;
import org.danielli.xultimate.context.image.model.ImageInfo;
import org.danielli.xultimate.context.image.model.ImageSize;
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
	public void addWatermarkImage(File srcImageFile, File destImageFile, File watermarkImageFile, Gravity gravity) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		
		if (watermarkImageFile != null) {
			Assert.notNull(gravity, "this gravity is required; it must not be null");
			if (gravity != Gravity.None) {
				BufferedImage watermarkBufferedImage = ImageUtils.createBufferedImage(watermarkImageFile);
				ImageCoordinate imageCoordinate = new ImageCoordinate(srcImageInfo.getImageSize(), new ImageSize(watermarkBufferedImage), gravity);
				srcBufferedImage = ImageUtils.addWatermarkImage(srcBufferedImage, watermarkBufferedImage, imageCoordinate, alpha, backgroundColor);
			}
		}
		ImageUtils.writeBufferedImage(srcBufferedImage, srcImageInfo.getImageFormat(), destImageFile, quality);
	}

	@Override
	public void addWatermarkImage(File srcImageFile, File destImageFile, File watermarkImageFile, ImageCoordinate imageCoordinate) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		
		if (watermarkImageFile != null) {
			Assert.notNull(imageCoordinate, "this imageCoordinate is required; it must not be null");
			BufferedImage watermarkBufferedImage = ImageUtils.createBufferedImage(watermarkImageFile);
			srcBufferedImage = ImageUtils.addWatermarkImage(srcBufferedImage, watermarkBufferedImage, imageCoordinate, alpha, backgroundColor);
		}
		
		ImageUtils.writeBufferedImage(srcBufferedImage, srcImageInfo.getImageFormat(), destImageFile, quality);
	}

	@Override
	public void addWatermarkText(File srcImageFile, File destImageFile, String watermarkText, Font watermarkTextFont, Color watermarkTextColor, Gravity gravity) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		
		if (StringUtils.isNotEmpty(watermarkText)) {
			Assert.notNull(gravity, "this gravity is required; it must not be null");
			if (gravity != Gravity.None) {
				ImageSize watermarkImageSize = ImageUtils.getFontAsImageWidth(watermarkText, watermarkTextFont);
				BufferedImage watermarkBufferedImage = ImageUtils.createTransluentBufferedImage(watermarkImageSize, watermarkText, watermarkTextFont, watermarkTextColor);
				ImageCoordinate imageCoordinate = new ImageCoordinate(new ImageSize(srcBufferedImage), watermarkImageSize, gravity);
				srcBufferedImage = ImageUtils.addWatermarkImage(srcBufferedImage, watermarkBufferedImage, imageCoordinate, alpha, backgroundColor);
			}
		}
		
		ImageUtils.writeBufferedImage(srcBufferedImage, srcImageInfo.getImageFormat(), destImageFile, quality);
	}

	@Override
	public void addWatermarkText(File srcImageFile, File destImageFile, String watermarkText, Font watermarkTextFont, Color watermarkTextColor, ImageCoordinate imageCoordinate) throws ImageException {
		BufferedImage srcBufferedImage = ImageUtils.createBufferedImage(srcImageFile);
		ImageInfo srcImageInfo = ImageUtils.getImageInfo(srcImageFile);
		
		if (StringUtils.isNotEmpty(watermarkText)) {
			Assert.notNull(imageCoordinate, "this imageCoordinate is required; it must not be null");
			ImageSize watermarkImageSize = ImageUtils.getFontAsImageWidth(watermarkText, watermarkTextFont);
			BufferedImage watermarkBufferedImage = ImageUtils.createTransluentBufferedImage(watermarkImageSize, watermarkText, watermarkTextFont, watermarkTextColor);
			
			srcBufferedImage = ImageUtils.addWatermarkImage(srcBufferedImage, watermarkBufferedImage, imageCoordinate, alpha, backgroundColor);
		}
		
		ImageUtils.writeBufferedImage(srcBufferedImage, srcImageInfo.getImageFormat(), destImageFile, quality);
	}

}
