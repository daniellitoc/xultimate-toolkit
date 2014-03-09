package org.danielli.xultimate.context.image2.config;

import java.awt.image.BufferedImage;
import java.io.File;

import org.danielli.xultimate.context.image2.ImageInfoException;

/**
 * 图片资源包装类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public final class WrapperImageResource implements ImageResource {
	/** 图片资源 */
	private ImageResource imageResource;
	
	public WrapperImageResource() {
	}
	
	public WrapperImageResource(File imageFile) {
		imageResource = new DefaultImageResource(imageFile);
	}
	
	@Override
	public File getImageFile() {
		if (imageResource == null)
			return null;
		return imageResource.getImageFile();
	}

	@Override
	public BufferedImage getBufferedImage() throws ImageInfoException {
		if (imageResource == null)
			return null;
		return imageResource.getBufferedImage();
	}

	@Override
	public ImageInfo getImageInfo() throws ImageInfoException {
		if (imageResource == null)
			return null;
		return imageResource.getImageInfo();
	}

	/**
	 * 获取图片资源。
	 * @return 图片资源。
	 */
	public ImageResource getImageResource() {
		return imageResource;
	}

	/**
	 * 设置图片资源。
	 * @param imageResource 图片资源。
	 */
	public void setImageResource(ImageResource imageResource) {
		this.imageResource = imageResource;
	}
}
