package org.danielli.xultimate.context.image.im4java.support;

import java.io.File;

import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.ImageResizeTemplate;
import org.danielli.xultimate.context.image.im4java.AbstractImageTemplate;
import org.danielli.xultimate.context.image.model.GeometryOperator;
import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageGeometry;
import org.danielli.xultimate.context.image.model.ImageSize;
import org.im4java.core.IMOperation;
import org.springframework.util.Assert;

/**
 * Im4java图片缩放模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageResizeTemplateImpl extends AbstractImageTemplate implements ImageResizeTemplate {

	@Override
	public void resizeImageAsFixed(File srcImageFile, File destImageFile, ImageSize imageSize) throws ImageException {
		Assert.notNull(srcImageFile, "this argument srcImageFile is required; it must not be null");
		Assert.notNull(destImageFile, "this argument destImageFile is required; it must not be null");
		
		IMOperation op = new IMOperation();
		op.addImage(srcImageFile.getPath());
		op.addRawArgs("-resize", new ImageGeometry(imageSize, GeometryOperator.Emphasize).toString());
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage(destImageFile.getPath());
		
		runOperation(op);

	}

	@Override
	public void resizeImageAsAutomatic(File srcImageFile, File destImageFile, ImageSize imageSize) throws ImageException {
		Assert.notNull(srcImageFile, "this argument srcImageFile is required; it must not be null");
		Assert.notNull(destImageFile, "this argument destImageFile is required; it must not be null");
		
		IMOperation op = new IMOperation();
		op.addImage(srcImageFile.getPath());
		op.addRawArgs("-resize", new ImageGeometry(imageSize, GeometryOperator.Maximum).toString());
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage(destImageFile.getPath());
		
		runOperation(op);
	}
	
	@Override
	public void resizeImageAsAutomatic(File srcImageFile, File destImageFile, ImageSize imageSize, Gravity gravity) throws ImageException {
		Assert.notNull(srcImageFile, "this argument srcImageFile is required; it must not be null");
		Assert.notNull(destImageFile, "this argument destImageFile is required; it must not be null");
		Assert.notNull(gravity, "this argument gravity is required; it must not be null");
		
		IMOperation op = new IMOperation();
		op.addImage(srcImageFile.getPath());
		op.addRawArgs("-resize", new ImageGeometry(imageSize, GeometryOperator.Maximum).toString());
		op.addRawArgs("-gravity", gravity.name());
		op.addRawArgs("-extent", new ImageGeometry(imageSize, GeometryOperator.Minimum).toString());
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage(destImageFile.getPath());
		
		runOperation(op);

	}
}
