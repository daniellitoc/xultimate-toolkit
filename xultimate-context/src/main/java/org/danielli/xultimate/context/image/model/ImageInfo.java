package org.danielli.xultimate.context.image.model;

/**
 * 图片信息。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageInfo {
	private ImageSize imageSize;
	private ImageFormat imageFormat;
	
	/**
	 * 创建图片信息。
	 * 
	 * @param width
	 * 			图片宽度。
	 * @param height
	 * 			图片高度。
	 * @param format
	 * 			图片格式。
	 */
	public ImageInfo(Integer width, Integer height, ImageFormat imageFormat) {
		this.imageSize = new ImageSize(width, height);
		this.imageFormat = imageFormat;
	}

	public ImageSize getImageSize() {
		return imageSize;
	}

	public ImageFormat getImageFormat() {
		return imageFormat;
	}

}
