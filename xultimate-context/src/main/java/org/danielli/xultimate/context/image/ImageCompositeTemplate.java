package org.danielli.xultimate.context.image;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageCoordinate;

/**
 * 图片组合模板工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface ImageCompositeTemplate {
	
	/**
	 * 添加水印图片。
	 * 
	 * @param srcImageFile
	 *            原图片文件。
	 * @param destImageFile
	 *            目标图片文件。
	 * @param watermarkImageFile
	 *            水印图片文件。
	 * @param gravity
	 *            水印图片文件的位置。
	 */
	void addWatermarkImage(File srcImageFile, File destImageFile, File watermarkImageFile, Gravity gravity) throws ImageException;

	/**
	 * 添加水印图片。
	 * 
	 * @param srcImageFile
	 *            原图片文件。
	 * @param destImageFile
	 *            目标图片文件。
	 * @param watermarkImageFile
	 *            水印图片文件。
	 * @param imageCoordinate
	 *            水印图片文件的位置。
	 */
	void addWatermarkImage(File srcImageFile, File destImageFile, File watermarkImageFile, ImageCoordinate imageCoordinate) throws ImageException;

	/**
	 * 添加水印文字。
	 * 
	 * @param srcImageFile
	 *            原图片文件。
	 * @param destImageFile
	 *            目标图片文件。
	 * @param watermarkText
	 *            水印文字。
	 * @param gravity
	 *            水印文字的位置。
	 */
	void addWatermarkText(File srcImageFile, File destImageFile, String watermarkText, Font watermarkTextFont, Color watermarkTextColor, Gravity gravity) throws ImageException;

	/**
	 * 添加水印文字。
	 * 
	 * @param srcImageFile
	 *            原图片文件。
	 * @param destImageFile
	 *            目标图片文件。
	 * @param watermarkText
	 *            水印文字。
	 * @param imageCoordinate
	 *            水印图片文件的位置。
	 */
	void addWatermarkText(File srcImageFile, File destImageFile, String watermarkText, Font watermarkTextFont, Color watermarkTextColor, ImageCoordinate imageCoordinate) throws ImageException;
}
