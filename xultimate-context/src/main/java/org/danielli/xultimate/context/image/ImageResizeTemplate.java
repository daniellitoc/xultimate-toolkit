package org.danielli.xultimate.context.image;

import java.io.File;

import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageSize;

/**
 * 图片缩放模板工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface ImageResizeTemplate {
	
	/**
	 * 按尺寸缩放图片。图片不会等比例缩放。
	 * 
	 * @param srcImageFile
	 * 				原图片文件。
	 * @param destImageFile
	 * 				目标图片文件。
	 * @param imageSize
	 * 				图片尺寸。
	 */
	void resizeImageAsFixed(File srcImageFile, File destImageFile, ImageSize imageSize) throws ImageException;
	
	/**
	 * 按尺寸缩放图片。图片会等比例缩放。
	 * 
	 * @param srcImageFile
	 * 				原图片文件。
	 * @param destImageFile
	 * 				目标图片文件。
	 * @param ImageSize
	 * 				图片尺寸。
	 */
	void resizeImageAsAutomatic(File srcImageFile, File destImageFile, ImageSize imageSize) throws ImageException;
	
	/**
	 * 按尺寸缩放图片。图片会等比例缩放，在指定方位以指定宽度与高度截取图片。
	 * 
	 * @param srcImageFile
	 * 				原图片文件。
	 * @param destImageFile
	 * 				目标图片文件。
	 * @param imageSize
	 * 				图片尺寸。
	 * @param gravity
	 * 				截取图片位置。
	 * 				
	 */
	void resizeImageAsAutomatic(File srcImageFile, File destImageFile, ImageSize imageSize, Gravity gravity) throws ImageException;
}
