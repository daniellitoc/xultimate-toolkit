package org.danielli.xultimate.context.image;

import java.io.File;

import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageGeometry;

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
	 * @param srcImageFile
	 * 				原图片文件。
	 * @param destImageFile
	 * 				目标图片文件。
	 * @param imageGeometry
	 * 				图片几何方位。包含图片尺寸和几何操作。
	 */
	void resizeImage(File srcImageFile, File destImageFile, ImageGeometry imageGeometry) throws ImageException;
	
	/**
	 * 按尺寸缩放图片。进行缩放后在指定方位以指定宽度与高度截取图片。
	 * 
	 * @param srcImageFile
	 * 				原图片文件。
	 * @param destImageFile
	 * 				目标图片文件。
	 * @param imageGeometry
	 * 				图片几何方位。包含图片尺寸和几何操作。
	 * @param gravity
	 * 				截取图片位置。
	 * 				
	 */
	void resizeImageAsFixed(File srcImageFile, File destImageFile, ImageGeometry imageGeometry, Gravity gravity) throws ImageException;
}
