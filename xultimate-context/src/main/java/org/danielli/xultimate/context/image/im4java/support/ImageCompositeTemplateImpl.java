package org.danielli.xultimate.context.image.im4java.support;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.danielli.xultimate.context.image.ImageCompositeTemplate;
import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.im4java.AbstractImageTemplate;
import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageCoordinate;
import org.im4java.core.CompositeCmd;
import org.im4java.core.IMOperation;
import org.springframework.util.Assert;

/**
 * Im4java图片组合模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageCompositeTemplateImpl extends AbstractImageTemplate implements ImageCompositeTemplate {

	private CompositeCmd compositeCmd;
	
	public void setCompositeCmd(CompositeCmd compositeCmd) {
		this.compositeCmd = compositeCmd;
	}

	@Override
	public void addWatermarkImage(File srcImageFile, File destImageFile, File watermarkImageFile, Gravity gravity) throws ImageException {
		Assert.notNull(srcImageFile, "this argument srcImageFile is required; it must not be null");
		Assert.notNull(destImageFile, "this argument destImageFile is required; it must not be null");
		
		IMOperation op = new IMOperation();
		
		if (watermarkImageFile != null) {
			Assert.notNull(gravity, "this argument gravity is required; it must not be null");
			if (gravity != Gravity.None) {
				op.addImage(watermarkImageFile.getPath());
				op.addRawArgs("-gravity", gravity.name());
			}
		}
		op.addRawArgs("-quality", quality.toString());
		op.addImage(srcImageFile.getPath());
		op.addImage(destImageFile.getPath());
		
		try {
			compositeCmd.run(op);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addWatermarkImage(File srcImageFile, File destImageFile, File watermarkImageFile, ImageCoordinate imageCoordinate) throws ImageException {
		Assert.notNull(srcImageFile, "this argument srcImageFile is required; it must not be null");
		Assert.notNull(destImageFile, "this argument destImageFile is required; it must not be null");
		
		IMOperation op = new IMOperation();
		
		if (watermarkImageFile != null) {
			Assert.notNull(imageCoordinate, "this  argument imageCoordinate is required; it must not be null");
			op.addImage(watermarkImageFile.getPath());
			op.addRawArgs("-geometry", imageCoordinate.toString());
		}
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage(srcImageFile.getPath());
		op.addImage(destImageFile.getPath());
		
		try {
			compositeCmd.run(op);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1.中文文本可能不显示
	 * 2.字体(样式)不能被正确识别到Java
	 */
	@Override
	public void addWatermarkText(File srcImageFile, File destImageFile, String watermarkText, Font watermarkTextFont, Color watermarkTextColor, Gravity gravity) throws ImageException {
		Assert.notNull(srcImageFile, "this argument srcImageFile is required; it must not be null");
		Assert.notNull(destImageFile, "this argument destImageFile is required; it must not be null");
		
		IMOperation op = new IMOperation();
		if (StringUtils.isNotEmpty(watermarkText)) {
			Assert.notNull(gravity, "this argument gravity is required; it must not be null");
			if (gravity != Gravity.None) {
				op.addRawArgs("-gravity", gravity.name());
				op.addRawArgs("-font", watermarkTextFont.getFontName());
				op.addRawArgs("-pointsize", String.valueOf(watermarkTextFont.getSize()));
				op.addRawArgs("-fill", getFill(watermarkTextColor));
				ImageCoordinate imageCoordinate = new ImageCoordinate(0, watermarkTextFont.getSize());
				op.addRawArgs("-draw", getDraw(new ImageCoordinate(imageCoordinate.getLatitude(), imageCoordinate.getLongitude() - watermarkTextFont.getSize()), watermarkText));
				op.addRawArgs("-quality", quality.toString()); 
			}
		}
		op.addImage(srcImageFile.getPath());
		op.addImage(destImageFile.getPath());

		runOperation(op); 
	}

	/**
	 * 1.中文文本可能不显示
	 * 2.字体(样式)不能被正确识别到Java
	 */
	@Override
	public void addWatermarkText(File srcImageFile, File destImageFile, String watermarkText, Font watermarkTextFont, Color watermarkTextColor, ImageCoordinate imageCoordinate) throws ImageException {
		Assert.notNull(srcImageFile, "this argument srcImageFile is required; it must not be null");
		Assert.notNull(destImageFile, "this argument destImageFile is required; it must not be null");
		
		IMOperation op = new IMOperation();
		op.addImage(srcImageFile.getPath());
		if (StringUtils.isNotEmpty(watermarkText)) {
			Assert.notNull(imageCoordinate, "this argument imageCoordinate is required; it must not be null");
			op.addRawArgs("-font", watermarkTextFont.getFontName());
			op.addRawArgs("-pointsize", String.valueOf(watermarkTextFont.getSize()));
			op.addRawArgs("-fill", getFill(watermarkTextColor));
			op.addRawArgs("-draw", getDraw(new ImageCoordinate(imageCoordinate.getLatitude(), imageCoordinate.getLongitude() + watermarkTextFont.getSize()), watermarkText));
			op.addRawArgs("-quality", quality.toString());  
		}
		op.addImage(destImageFile.getPath());
		
		runOperation(op);
	}
	
	private String getDraw(ImageCoordinate coordinate, String watermarkText) {
		StringBuilder builder = new StringBuilder();
		builder.append("text ").append(coordinate.getLatitude()).append(',').append(coordinate.getLongitude());
		builder.append(" \'").append(watermarkText).append("\'");
		return builder.toString();
	} 
	
	private String getFill(Color color) {
		StringBuilder builder = new StringBuilder();
		builder.append("rgb(").append(color.getRed());
		builder.append(",").append(color.getGreen());
		builder.append(",").append(color.getBlue()).append(")");
		return builder.toString();
	} 

}
