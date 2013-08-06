package org.danielli.xultimate.context.image;

import java.io.File;

import org.danielli.xultimate.context.image.model.ImageInfo;

/**
 * 图片信息模板工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface ImageInfoTemplate {
	
	/**
	 * 获取图片信息。
	 * 
	 * @param imageFile
	 * 				图片文件。
	 * @return		图片信息。
	 */
	ImageInfo getImageInfo(File imageFile) throws ImageInfoException;
	
	/**
	 * 转换图片。
	 * 
	 * @param srcImageFile
	 * 				原图片文件。
	 * @param destImageFile
	 * 				目标图片文件。
	 */
	void convertImage(File srcImageFile, File destImageFile) throws ImageException;
}
