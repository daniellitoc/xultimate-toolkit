package org.danielli.xultimate.context.image2.im4java.support;

import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.ImageResizeTemplate;
import org.danielli.xultimate.context.image2.awt.ImageUtils;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.GeometryOperator;
import org.danielli.xultimate.context.image2.config.Gravity;
import org.danielli.xultimate.context.image2.config.ImageGeometry;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;
import org.danielli.xultimate.context.image2.im4java.AbstractIm4javaImageTemplate;
import org.danielli.xultimate.util.Assert;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

/**
 * Im4java图片缩放模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageResizeTemplateImpl extends AbstractIm4javaImageTemplate implements ImageResizeTemplate {
	
	@Override
	public void resizeImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, ImageGeometry imageGeometry) throws ImageException {
		Assert.notNull(srcImageResource, "this argument srcImageResource is required; it must not be null");
		Assert.notNull(destImageResource, "this argument destImageResource is required; it must not be null");
		
		IMOperation op = new IMOperation();
		op.addRawArgs("-sample", new ImageGeometry(imageGeometry.getImageSize(), imageGeometry.getOperator()).toString());
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage();
		
		runOperation(new ConvertCmd(useGraphicsMagick), op, destImageResource, srcImageResource);
	}
	
	@Override
	public void resizeImageAsFixed(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, ImageGeometry imageGeometry, Gravity gravity) throws ImageException {
		Assert.notNull(srcImageResource, "this argument srcImageResource is required; it must not be null");
		Assert.notNull(destImageResource, "this argument destImageResource is required; it must not be null");
		Assert.notNull(gravity, "this argument gravity is required; it must not be null");
		
		IMOperation op = new IMOperation();
		op.addRawArgs("-sample", imageGeometry.toString());
		op.addRawArgs("-gravity", gravity.name());
		op.addRawArgs("-extent", new ImageGeometry(imageGeometry.getImageSize(), GeometryOperator.Minimum).toString());
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage();
		
		runOperation(new ConvertCmd(useGraphicsMagick), op, destImageResource, srcImageResource);
	}
}
