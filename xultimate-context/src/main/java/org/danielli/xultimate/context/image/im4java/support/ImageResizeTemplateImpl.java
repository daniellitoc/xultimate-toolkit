package org.danielli.xultimate.context.image.im4java.support;

import java.io.File;

import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.ImageResizeTemplate;
import org.danielli.xultimate.context.image.awt.ImageUtils;
import org.danielli.xultimate.context.image.im4java.AbstractIm4javaImageTemplate;
import org.danielli.xultimate.context.image.model.GeometryOperator;
import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageGeometry;
import org.danielli.xultimate.util.Assert;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;

/**
 * Im4java图片缩放模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageResizeTemplateImpl extends AbstractIm4javaImageTemplate implements ImageResizeTemplate {

	@Override
	public ImageCommand createImageCommand(IMOperation operation, Object... params) {
		return new ConvertCmd(useGraphicsMagick);
	}
	
	@Override
	public void resizeImage(File srcImageFile, File destImageFile, ImageGeometry imageGeometry) throws ImageException {
		Assert.notNull(srcImageFile, "this argument srcImageFile is required; it must not be null");
		Assert.notNull(destImageFile, "this argument destImageFile is required; it must not be null");
		
		IMOperation op = new IMOperation();
		op.addRawArgs("-sample", new ImageGeometry(imageGeometry.getImageSize(), imageGeometry.getOperator()).toString());
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage(srcImageFile.getPath());
		op.addImage(destImageFile.getPath());
		
		runOperation(op);
	}
	
	@Override
	public void resizeImageAsFixed(File srcImageFile, File destImageFile, ImageGeometry imageGeometry, Gravity gravity) throws ImageException {
		Assert.notNull(srcImageFile, "this argument srcImageFile is required; it must not be null");
		Assert.notNull(destImageFile, "this argument destImageFile is required; it must not be null");
		Assert.notNull(gravity, "this argument gravity is required; it must not be null");
		
		IMOperation op = new IMOperation();
		op.addRawArgs("-sample", imageGeometry.toString());
		op.addRawArgs("-gravity", gravity.name());
		op.addRawArgs("-extent", new ImageGeometry(imageGeometry.getImageSize(), GeometryOperator.Minimum).toString());
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage(srcImageFile.getPath());
		op.addImage(destImageFile.getPath());

		runOperation(op);
	}
}
