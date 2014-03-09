package org.danielli.xultimate.context.image2;

import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.Gravity;
import org.danielli.xultimate.context.image2.config.ImageGeometry;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;

/**
 * 图片缩放模板工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface ImageResizeTemplate {
	
	/**
	 * 按尺寸缩放图片。
	 * 
	 * @param srcImageResource
	 * 				原图片资源。
	 * @param destImageResource
	 * 				目标图片资源。
	 * @param imageGeometry
	 * 				图片几何方位。包含图片尺寸和几何操作。
	 */
	void resizeImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, ImageGeometry imageGeometry) throws ImageException;
	
	/**
	 * 按尺寸缩放图片。进行缩放后在指定方位以指定宽度与高度截取图片。
	 * 
	 * @param srcImageResource
	 * 				原图片资源。
	 * @param destImageResource
	 * 				目标图片资源。
	 * @param imageGeometry
	 * 				图片几何方位。包含图片尺寸和几何操作。
	 * @param gravity
	 * 				截取图片位置。
	 * 				
	 */
	void resizeImageAsFixed(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, ImageGeometry imageGeometry, Gravity gravity) throws ImageException;
}
