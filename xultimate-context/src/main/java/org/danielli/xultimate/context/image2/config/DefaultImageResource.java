package org.danielli.xultimate.context.image2.config;

import java.awt.image.BufferedImage;
import java.io.File;

import org.danielli.xultimate.context.image2.ImageInfoException;
import org.danielli.xultimate.context.image2.awt.ImageUtils;

/**
 * 图片资源默认实现。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public final class DefaultImageResource implements ImageResource {
	/** 图片文件 */
	protected File imageFile;
	/** 缓冲图片 */
	protected BufferedImage bufferedImage;
	/** 图片信息 */
	protected ImageInfo imageInfo;
	
	public DefaultImageResource(File imageFile) {
		this.imageFile = imageFile;
	}

	public DefaultImageResource(BufferedImage bufferedImage, ImageFormat imageFormat) {
		this.bufferedImage = bufferedImage;
		this.imageInfo = new ImageInfo(bufferedImage.getWidth(), bufferedImage.getHeight(), imageFormat);
	}

	@Override
	public File getImageFile() {
		return imageFile;
	}

	@Override
	public BufferedImage getBufferedImage() throws ImageInfoException {
		if (imageFile != null && bufferedImage == null) {
			synchronized (this) {
				if (imageFile != null && bufferedImage == null) {
					bufferedImage = ImageUtils.createBufferedImage(imageFile);
				}
			}
		}
		return bufferedImage;
	}

	@Override
	public ImageInfo getImageInfo() throws ImageInfoException {
		if (imageFile != null && imageInfo == null) {
			synchronized (this) {
				if (imageFile != null && imageInfo == null) {
					imageInfo = ImageUtils.getImageInfo(imageFile);
				}
			}
		}
		return imageInfo;
	}
}
