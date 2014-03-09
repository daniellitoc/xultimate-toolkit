package org.danielli.xultimate.context.image2.config;

import java.awt.image.BufferedImage;

/**
 * 图片尺寸。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public final class ImageSize {
	
	private Integer width;
	private Integer height;
	
	/**
	 * 创建图片尺寸。
	 * 
	 * @param width
	 * 				图片宽度。
	 * @param height
	 * 				图片高度。
	 */
	public ImageSize(Integer width, Integer height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 创建图片尺寸。
	 * 
	 * @param bufferedImage 缓冲图片。
	 */
	public ImageSize(BufferedImage bufferedImage) {
		this(bufferedImage.getWidth(), bufferedImage.getHeight());
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}
	
	public void setCrop(ImageSize srcImageSize) {
		if (srcImageSize.getWidth() < width) {
			width = srcImageSize.getWidth();
		}
		if (srcImageSize.getHeight() < height) {
			height = srcImageSize.getHeight();
		}
	}
	
	@Override
	public String toString() {		
		/*
		 * 构造im4java格式。
		 */
		StringBuilder builder = new StringBuilder();
		if (width != null) {
			builder.append(width);
		}
		if (height != null) {
			builder.append('x').append(height);
	    }
		return builder.toString();
	}

}
