package org.danielli.xultimate.context.image2.im4java.support;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.context.image2.ImageCompositeTemplate;
import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.awt.ImageUtils;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.Gravity;
import org.danielli.xultimate.context.image2.config.ImageCoordinate;
import org.danielli.xultimate.context.image2.config.ImageFormat;
import org.danielli.xultimate.context.image2.config.ImageSize;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;
import org.danielli.xultimate.context.image2.im4java.AbstractIm4javaImageTemplate;
import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.StringUtils;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

/**
 * Im4java图片组合模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageCompositeTemplateImpl extends AbstractIm4javaImageTemplate implements ImageCompositeTemplate {

	private Integer alpha = 100;
	
	public void setAlpha(Integer alpha) {
		this.alpha = alpha;
	}
	
	@Override
	public void addWatermarkImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, DefaultImageResource watermarkImageResource, Gravity gravity) throws ImageException {
		Assert.notNull(srcImageResource, "this argument srcImageResource is required; it must not be null");
		Assert.notNull(destImageResource, "this argument destImageResource is required; it must not be null");
		
		IMOperation op = new IMOperation();
		if (watermarkImageResource != null) {
			Assert.notNull(gravity, "this argument gravity is required; it must not be null");
			Assert.isTrue(alpha >= 0 && alpha <= 100, "this argument alpha is required; it must between 0 and 100");
			if (gravity != Gravity.None) {
				op.addRawArgs("-gravity", gravity.name());
				op.addRawArgs("-dissolve", alpha.toString());
				op.addImage();
			}
		}
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString());
		op.addImage();
		if (watermarkImageResource != null) {
			runOperation(new CompositeCmd(useGraphicsMagick), op, destImageResource, watermarkImageResource, srcImageResource);
		} else {
			runOperation(new ConvertCmd(useGraphicsMagick), op, destImageResource, srcImageResource);
		}
	}
	
	@Override
	public void addWatermarkImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, DefaultImageResource watermarkImageResource, ImageCoordinate imageCoordinate) throws ImageException {
		Assert.notNull(srcImageResource, "this argument srcImageResource is required; it must not be null");
		Assert.notNull(destImageResource, "this argument destImageResource is required; it must not be null");
		
		IMOperation op = new IMOperation();
		if (watermarkImageResource != null) {
			Assert.notNull(imageCoordinate, "this  argument imageCoordinate is required; it must not be null");
			Assert.isTrue(alpha >= 0 && alpha <= 100, "this argument alpha is required; it must between 0 and 100");
			op.addRawArgs("-geometry", imageCoordinate.toString());
			op.addRawArgs("-dissolve", alpha.toString());
			op.addImage();
		}
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage();
		
		if (watermarkImageResource != null) {
			runOperation(new CompositeCmd(useGraphicsMagick), op, destImageResource, watermarkImageResource, srcImageResource);
		} else {
			runOperation(new ConvertCmd(useGraphicsMagick), op, destImageResource, srcImageResource);
		}
	}

	@Override
	public void addWatermarkText(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, String watermarkText, Font watermarkTextFont, Color watermarkTextColor, Gravity gravity) throws ImageException {
		Assert.notNull(srcImageResource, "this argument srcImageResource is required; it must not be null");
		Assert.notNull(destImageResource, "this argument destImageResource is required; it must not be null");
		
		DefaultImageResource watermarkImageResource = null;
		IMOperation op = new IMOperation();
		if (StringUtils.isNotEmpty(watermarkText)) {
			Assert.notNull(gravity, "this argument gravity is required; it must not be null");
			Assert.isTrue(alpha >= 0 && alpha <= 100, "this argument alpha is required; it must between 0 and 100");
			if (gravity != Gravity.None) {
				op.addRawArgs("-gravity", gravity.name());
				op.addRawArgs("-dissolve", alpha.toString());
				op.addImage();
				ImageSize watermarkImageSize = ImageUtils.getFontAsImageWidth(watermarkText, watermarkTextFont);
				BufferedImage watermarkBufferedImage = ImageUtils.createTransluentBufferedImage(watermarkImageSize, watermarkText, watermarkTextFont, watermarkTextColor);
				watermarkImageResource = new DefaultImageResource(watermarkBufferedImage, ImageFormat.JPEG);
			}
		}
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString());
		op.addImage();
		
		if (watermarkImageResource != null) {
			runOperation(new CompositeCmd(useGraphicsMagick), op, destImageResource, watermarkImageResource, srcImageResource);
		} else {
			runOperation(new ConvertCmd(useGraphicsMagick), op, destImageResource, srcImageResource);
		}
//		中文和字体样式无法应用	
//		IMOperation op = new IMOperation();
//		if (StringUtils.isNotEmpty(watermarkText)) {
//			Assert.notNull(gravity, "this argument gravity is required; it must not be null");
//			if (gravity != Gravity.None) {
//				op.addRawArgs("-gravity", gravity.name());
//				op.addRawArgs("-font", watermarkTextFont.getFontName());
//				op.addRawArgs("-pointsize", String.valueOf(watermarkTextFont.getSize()));
//				op.addRawArgs("-fill", getFill(watermarkTextColor));
//				ImageCoordinate imageCoordinate = new ImageCoordinate(0, watermarkTextFont.getSize());
//				op.addRawArgs("-draw", getDraw(new ImageCoordinate(imageCoordinate.getLatitude(), imageCoordinate.getLongitude() - watermarkTextFont.getSize()), watermarkText));
//			}
//		}
//		op.addRawArgs("-quality", quality.toString()); 
//		op.addImage(srcImageFile.getPath());
//		op.addImage(destImageFile.getPath());

//		imageCommandThreadLocal.set(new ConvertCmd(useGraphicsMagick));
//		runOperation(op);
	}

	@Override
	public void addWatermarkText(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, String watermarkText, Font watermarkTextFont, Color watermarkTextColor, ImageCoordinate imageCoordinate) throws ImageException {
		Assert.notNull(srcImageResource, "this argument srcImageResource is required; it must not be null");
		Assert.notNull(destImageResource, "this argument destImageResource is required; it must not be null");
		
		DefaultImageResource watermarkImageResource = null;
		IMOperation op = new IMOperation();
		if (StringUtils.isNotEmpty(watermarkText)) {
			Assert.notNull(imageCoordinate, "this  argument imageCoordinate is required; it must not be null");
			Assert.isTrue(alpha >= 0 && alpha <= 100, "this argument alpha is required; it must between 0 and 100");
			op.addRawArgs("-geometry", imageCoordinate.toString());
			op.addRawArgs("-dissolve", alpha.toString());
			op.addImage();
			ImageSize watermarkImageSize = ImageUtils.getFontAsImageWidth(watermarkText, watermarkTextFont);
			BufferedImage watermarkBufferedImage = ImageUtils.createTransluentBufferedImage(watermarkImageSize, watermarkText, watermarkTextFont, watermarkTextColor);
			watermarkImageResource = new DefaultImageResource(watermarkBufferedImage, ImageFormat.JPEG);
		}
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString());
		op.addImage();
		
		if (watermarkImageResource != null) {
			runOperation(new CompositeCmd(useGraphicsMagick), op, destImageResource, watermarkImageResource, srcImageResource);
		} else {
			runOperation(new ConvertCmd(useGraphicsMagick), op, destImageResource, srcImageResource);
		}
//		中文和字体样式无法应用
//		IMOperation op = new IMOperation();
//		op.addImage(srcImageFile.getPath());
//		if (StringUtils.isNotEmpty(watermarkText)) {
//			Assert.notNull(imageCoordinate, "this argument imageCoordinate is required; it must not be null");
//			op.addRawArgs("-font", watermarkTextFont.getFontName());
//			op.addRawArgs("-pointsize", String.valueOf(watermarkTextFont.getSize()));
//			op.addRawArgs("-fill", getFill(watermarkTextColor));
//			op.addRawArgs("-draw", getDraw(new ImageCoordinate(imageCoordinate.getLatitude(), imageCoordinate.getLongitude() + watermarkTextFont.getSize()), watermarkText));
//		}
//		op.addRawArgs("-quality", quality.toString());  
//		op.addImage(destImageFile.getPath());
//		
//		imageCommandThreadLocal.set(new ConvertCmd(useGraphicsMagick));
//		runOperation(op);
	}
	
//	private String getDraw(ImageCoordinate coordinate, String watermarkText) {
//		StringBuilder builder = new StringBuilder();
//		builder.append("text ").append(coordinate.getLatitude()).append(',').append(coordinate.getLongitude());
//		builder.append(" \'").append(watermarkText).append("\'");
//		return builder.toString();
//	} 
//	
//	private String getFill(Color color) {
//		StringBuilder builder = new StringBuilder();
//		builder.append("rgb(").append(color.getRed());
//		builder.append(",").append(color.getGreen());
//		builder.append(",").append(color.getBlue()).append(")");
//		return builder.toString();
//	} 

}
