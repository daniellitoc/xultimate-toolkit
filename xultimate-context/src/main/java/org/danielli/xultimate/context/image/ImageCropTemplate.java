package org.danielli.xultimate.context.image;

import java.io.File;

import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageGeometryCoordinate;
import org.danielli.xultimate.context.image.model.ImageSize;

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
	 * @param srcImageFile
	 * 				原图片文件。
	 * @param destImageFile
	 * 				目标图片文件。
	 * @param imageSize
	 * 				图片尺寸。
	 * @param gravity
	 * 				剪裁图片位置。
	 */
	void cropImage(File srcImageFile, File destImageFile, ImageSize imageSize, Gravity gravity) throws ImageException;
	
	/**
	 * 剪裁图片。
	 * 
	 * @param srcImageFile
	 * 				原图片文件。
	 * @param destImageFile
	 * 				目标图片文件。
	 * @param imageGeometryCoordinate
	 * 				图片剪裁几何坐标，包含图片尺寸和剪裁图片位置。		。
	 */
	void cropImage(File srcImageFile, File destImageFile, ImageGeometryCoordinate imageGeometryCoordinate) throws ImageException;
}
