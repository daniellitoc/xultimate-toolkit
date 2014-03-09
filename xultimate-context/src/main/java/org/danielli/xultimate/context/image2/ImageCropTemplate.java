package org.danielli.xultimate.context.image2;

import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.Gravity;
import org.danielli.xultimate.context.image2.config.ImageGeometryCoordinate;
import org.danielli.xultimate.context.image2.config.ImageSize;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;

/**
 * 图片剪裁模板工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface ImageCropTemplate {
	
	/**
	 * 剪裁图片。
	 * 
	 * @param srcImageResource
	 * 				原图片资源。
	 * @param destImageResource
	 * 				目标图片资源。
	 * @param imageSize
	 * 				图片尺寸。
	 * @param gravity
	 * 				剪裁图片位置。
	 */
	void cropImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, ImageSize imageSize, Gravity gravity) throws ImageException;
	
	/**
	 * 剪裁图片。
	 * 
	 * @param srcImageResource
	 * 				原图片资源。
	 * @param destImageResource
	 * 				目标图片资源。
	 * @param imageGeometryCoordinate
	 * 				图片剪裁几何坐标，包含图片尺寸和剪裁图片位置。		。
	 */
	void cropImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, ImageGeometryCoordinate imageGeometryCoordinate) throws ImageException;
}
