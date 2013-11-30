package org.danielli.xultimate.context.image.im4java.support;

import java.io.File;

import org.danielli.xultimate.context.image.ImageCropTemplate;
import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.awt.ImageUtils;
import org.danielli.xultimate.context.image.im4java.AbstractIm4javaImageTemplate;
import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageCoordinate;
import org.danielli.xultimate.context.image.model.ImageGeometryCoordinate;
import org.danielli.xultimate.context.image.model.ImageSize;
import org.danielli.xultimate.util.Assert;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;

/**
 * Im4java图片剪裁模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageCropTemplateImpl extends AbstractIm4javaImageTemplate implements ImageCropTemplate {

	@Override
	public ImageCommand createImageCommand(IMOperation operation, Object... params) {
		return new ConvertCmd(useGraphicsMagick);
	}
	
	@Override
	public void cropImage(File srcImageFile, File destImageFile, ImageSize imageSize, Gravity gravity) throws ImageException {
		Assert.notNull(srcImageFile, "this srcImageFile is required; it must not be null");
		Assert.notNull(destImageFile, "this destImageFile is required; it must not be null");
		Assert.isTrue(!(gravity == null || gravity == Gravity.None), "this gravity is required; it must not be None");
		
		IMOperation op = new IMOperation();
		op.addRawArgs("-gravity", gravity.name());
		op.addRawArgs("-crop", new ImageGeometryCoordinate(imageSize, ImageCoordinate.DEFAULT_IMAGE_COORDINATE).toString());
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage(srcImageFile.getPath());
		op.addImage(destImageFile.getPath());
		
		runOperation(op);
	}
	
	@Override
	public void cropImage(File srcImageFile, File destImageFile, ImageGeometryCoordinate imageGeometryCoordinate) throws ImageException {
		Assert.notNull(srcImageFile, "this srcImageFile is required; it must not be null");
		Assert.notNull(destImageFile, "this destImageFile is required; it must not be null");
		
		IMOperation op = new IMOperation();
		op.addRawArgs("-crop", imageGeometryCoordinate.toString());
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage(srcImageFile.getPath());
		op.addImage(destImageFile.getPath());
		
		runOperation(op);
	}
}
