package org.danielli.xultimate.context.image.im4java.support;

import java.io.File;

import org.danielli.xultimate.context.image.ImageException;
import org.danielli.xultimate.context.image.ImageInfoException;
import org.danielli.xultimate.context.image.ImageInfoTemplate;
import org.danielli.xultimate.context.image.awt.ImageUtils;
import org.danielli.xultimate.context.image.im4java.AbstractIm4javaImageTemplate;
import org.danielli.xultimate.context.image.model.ImageFormat;
import org.danielli.xultimate.context.image.model.ImageInfo;
import org.danielli.xultimate.util.Assert;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;
import org.im4java.core.Info;

/**
 * Im4java图片信息模板工具实现类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageInfoTemplateImpl extends AbstractIm4javaImageTemplate implements ImageInfoTemplate {

	@Override
	public ImageCommand createImageCommand(IMOperation operation, Object... params) {
		return new ConvertCmd(useGraphicsMagick);
	}
	
	@Override
	public ImageInfo getImageInfo(File imageFile) throws ImageInfoException {
		Assert.notNull(imageFile, "this argument imageFile is required; it must not be null");
		try {
			Info info = new Info(imageFile.getPath(), true);
			return new ImageInfo(info.getImageWidth(), info.getImageHeight(), ImageFormat.valueOf(info.getImageFormat()));
		} catch (Exception e) {
			throw new ImageInfoException(e.getMessage(), e);
		}
	}

	@Override
	public void convertImage(File srcImageFile, File destImageFile) throws ImageException {
		Assert.notNull(srcImageFile, "this argument srcImageFile is required; it must not be null");
		Assert.notNull(destImageFile, "this argument destImageFile is required; it must not be null");
		
		IMOperation op = new IMOperation();
		op.addRawArgs("-background", ImageUtils.toHexEncoding(backgroundColor));
		op.addRawArgs("-quality", quality.toString()); 
		op.addImage(srcImageFile.getPath());
		op.addImage(destImageFile.getPath());
		
		runOperation(op);
	}
}
