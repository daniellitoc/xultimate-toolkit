package org.danielli.xultimate.context.image.model;

import org.danielli.xultimate.util.Assert;

/**
 * 图片几何坐标。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageGeometryCoordinate {
	
	private ImageSize imageSize;
	
	private ImageCoordinate imageCoordinate;

	/**
	 * 创建图片几何坐标。
	 * 
	 * @param imageSize
	 * 				图片尺寸。
	 * @param imageCoordinate
	 * 				图片坐标。
	 */
	public ImageGeometryCoordinate(ImageSize imageSize, ImageCoordinate imageCoordinate) {
		Assert.notNull(imageSize, "this argument coordinate is required; it must not be null");
		Assert.notNull(imageCoordinate, "this argument coordinate is required; it must not be null");
		this.imageSize = imageSize;
		this.imageCoordinate = imageCoordinate;
	}
	
	/**
	 * 获取图片大小。
	 * 
	 * @return		图片大小。
	 */
	public ImageSize getImageSize() {
		return imageSize;
	}

	/**
	 * 获取图片坐标。
	 * 
	 * @return		图片坐标。
	 */
	public ImageCoordinate getImageCoordinate() {
		return imageCoordinate;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(imageSize).append(imageCoordinate);
		return builder.toString();
	}
}
