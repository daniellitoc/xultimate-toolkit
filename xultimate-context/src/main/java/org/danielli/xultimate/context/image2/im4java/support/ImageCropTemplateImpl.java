package org.danielli.xultimate.context.image2.im4java.support;

import org.danielli.xultimate.context.image2.ImageCropTemplate;
import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.awt.ImageUtils;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.Gravity;
import org.danielli.xultimate.context.image2.config.ImageCoordinate;
import org.danielli.xultimate.context.image2.config.ImageGeometryCoordinate;
import org.danielli.xultimate.context.image2.config.ImageSize;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;
import org.danielli.xultimate.context.image2.im4java.AbstractIm4javaImageTemplate;
import org.danielli.xultimate.util.Assert;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

/**
 * Im4java图片剪裁模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageCropTemplateImpl extends AbstractIm4javaImageTemplate implements ImageCropTemplate {
	
	@Override
	public void cropImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, ImageSize imageSize, Gravity gravity) throws ImageException {
		Assert.notNull(srcImageResource, "this srcImageResource is required; it must not be null");
		Assert.notNull(destImageResource, "this destImageResource is required; it must not be null");
		Assert.isTrue(!(gravity == null || gravity == Gravity.None), "this gravity is required; it must not be None");
		
		IMOperation op = new IMOperation();
		op.addRawArgs("-gravity", gravity.name());
		op.addRawArgs("-crop", new ImageGeometryCoordinate(imageSize, ImageCoordinate.DEFAULT_IMAGE_COORDINATE).toString());
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage();
		
		runOperation(new ConvertCmd(useGraphicsMagick), op, destImageResource, srcImageResource);
	}
	
	@Override
	public void cropImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, ImageGeometryCoordinate imageGeometryCoordinate) throws ImageException {
		Assert.notNull(srcImageResource, "this srcImageResource is required; it must not be null");
		Assert.notNull(destImageResource, "this destImageResource is required; it must not be null");
		
		IMOperation op = new IMOperation();
		op.addRawArgs("-crop", imageGeometryCoordinate.toString());
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage();
		
		runOperation(new ConvertCmd(useGraphicsMagick), op, destImageResource, srcImageResource);
	}
}
