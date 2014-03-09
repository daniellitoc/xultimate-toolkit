package org.danielli.xultimate.context.image2.config;

import org.danielli.xultimate.util.Assert;

/**
 * 图片坐标。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class ImageCoordinate {
	
	public static final ImageCoordinate DEFAULT_IMAGE_COORDINATE = new ImageCoordinate(0, 0);
	
	private Integer latitude;
	private Integer longitude;
	
	/**
	 * 创建图片坐标。
	 * 
	 * @param latitude
	 * 				图片纬度。
	 * @param longitude
	 * 				图片经度。
	 */
	public ImageCoordinate(Integer latitude, Integer longitude) {
		Assert.notNull(latitude, "this argument latitude is required; it must not be null");
		Assert.notNull(longitude, "this argument longitude is required; it must not be null");
		
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * 创建图片坐标。
	 * 
	 * @param srcImageSize
	 * 				原图片尺寸。
	 * @param size
	 * 				目标图片尺寸。
	 * @param gravity
	 * 				目标图片在原图片中的方位。
	 */
	public ImageCoordinate(ImageSize srcImageSize, ImageSize destImageSize, Gravity gravity) {
		Assert.isTrue(!(gravity == null || gravity == Gravity.None), "this argument gravity is required; it must not be None");
		
		Integer srcImageWidth = srcImageSize.getWidth();
		Integer destImageWidth = destImageSize.getWidth();
//		Assert.isTrue(srcImageWidth.compareTo(destImageWidth) >= 0, "this argument srcImageSize width must greater than argument destImageSize width");
		
		Integer srcImageHeight = srcImageSize.getHeight();
		Integer destImageHeight = destImageSize.getHeight();
//		Assert.isTrue(srcImageHeight.compareTo(destImageHeight) >= 0, "this  argument srcImageSize height must greater than argument destImageSize height");
		
		if (gravity == Gravity.NorthWest) {
			latitude = 0;
			longitude = 0;
		} else if (gravity == Gravity.North) {
			latitude = (int) Math.round((srcImageWidth - destImageWidth) / 2.0);
			longitude = 0;
		} else if (gravity == Gravity.NorthEast) {
			latitude  = srcImageWidth - destImageWidth;
			longitude = 0;
		} else if (gravity == Gravity.West) {
			latitude = 0;
			longitude = (int) Math.round((srcImageHeight - destImageHeight) / 2.0);
		} else if (gravity == Gravity.Center) {
			latitude = (int) Math.round((srcImageWidth - destImageWidth) / 2.0);
			longitude = (int) Math.round((srcImageHeight - destImageHeight) / 2.0);
		} else if (gravity == Gravity.East) {
			latitude = srcImageWidth - destImageWidth;
			longitude = (int) Math.round((srcImageHeight - destImageHeight) / 2.0);
		} else if (gravity == Gravity.SouthWest) {
			latitude = 0;
			longitude = srcImageHeight - destImageHeight;
		} else if (gravity == Gravity.South) {
			latitude = (int) Math.round((srcImageWidth - destImageWidth) / 2.0);
			longitude = srcImageHeight - destImageHeight;
		} else if (gravity == Gravity.SouthEast) {
			latitude = srcImageWidth - destImageWidth;
			longitude = srcImageHeight - destImageHeight;
		}
	}
	
	/**
	 * 获取纬度。
	 * 
	 * @return	纬度(X坐标)。
	 */
	public Integer getLatitude() {
		return latitude;
	}
	/**
	 * 获取经度。
	 * 
	 * @return　经度(Y坐标)。
	 */
	public Integer getLongitude() {
		return longitude;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (latitude >= 0) {
			builder.append('+');
		}
		builder.append(latitude);
		if (longitude >= 0) {
			builder.append('+');
		}
		builder.append(longitude);
		return builder.toString();
	}
}
