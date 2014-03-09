package org.danielli.xultimate.context.image2.config;

import java.awt.image.BufferedImage;
import java.io.File;

import org.danielli.xultimate.context.image2.ImageInfoException;

/**
 * 图片资源。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface ImageResource {

	/**
	 * 获取图片文件。
	 * @return 图片文件。
	 */
	File getImageFile();
	
	/**
	 * 获取缓冲图片。
	 * @return 缓冲图片。
	 */
	BufferedImage getBufferedImage() throws ImageInfoException;
	
	/**
	 * 获取图片信息。
	 * @return 图片信息。
	 */
	ImageInfo getImageInfo() throws ImageInfoException;
}
