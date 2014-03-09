package org.danielli.xultimate.context.image2.im4java.support;

import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.ImageInfoTemplate;
import org.danielli.xultimate.context.image2.awt.ImageUtils;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;
import org.danielli.xultimate.context.image2.im4java.AbstractIm4javaImageTemplate;
import org.danielli.xultimate.util.Assert;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

/**
 * Im4java图片信息模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageInfoTemplateImpl extends AbstractIm4javaImageTemplate implements ImageInfoTemplate {
	
	@Override
	public void convertImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource) throws ImageException {
		Assert.notNull(srcImageResource, "this argument srcImageResource is required; it must not be null");
		Assert.notNull(destImageResource, "this argument destImageResource is required; it must not be null");
		
		IMOperation op = new IMOperation();
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage();
		
		runOperation(new ConvertCmd(useGraphicsMagick), op, destImageResource, srcImageResource);
	}
}
