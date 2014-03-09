package org.danielli.xultimate.context.image2;

import java.awt.Color;
import java.awt.Font;

import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.Gravity;
import org.danielli.xultimate.context.image2.config.ImageCoordinate;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;

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
	 * @param srcImageResource
	 * 				原图片资源。
	 * @param destImageResource
	 * 				目标图片资源。
	 * @param watermarkImageResource
	 *            水印图片资源。
	 * @param gravity
	 *            水印图片文件的位置。
	 */
	void addWatermarkImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, DefaultImageResource watermarkImageResource, Gravity gravity) throws ImageException;

	/**
	 * 添加水印图片。
	 * 
	 * @param srcImageResource
	 * 				原图片资源。
	 * @param destImageResource
	 * 				目标图片资源。
	 * @param watermarkImageResource
	 *            水印图片资源。
	 * @param imageCoordinate
	 *            水印图片文件的位置。
	 */
	void addWatermarkImage(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, DefaultImageResource watermarkImageResource, ImageCoordinate imageCoordinate) throws ImageException;

	/**
	 * 添加水印文字。
	 * 
	 * @param srcImageResource
	 * 				原图片资源。
	 * @param destImageResource
	 * 				目标图片资源。
	 * @param watermarkText
	 *            水印文字。
	 * @param gravity
	 *            水印文字的位置。
	 */
	void addWatermarkText(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, String watermarkText, Font watermarkTextFont, Color watermarkTextColor, Gravity gravity) throws ImageException;

	/**
	 * 添加水印文字。
	 * 
	 * @param srcImageResource
	 * 				原图片资源。
	 * @param destImageResource
	 * 				目标图片资源。
	 * @param watermarkText
	 *            水印文字。
	 * @param imageCoordinate
	 *            水印图片文件的位置。
	 */
	void addWatermarkText(DefaultImageResource srcImageResource, WrapperImageResource destImageResource, String watermarkText, Font watermarkTextFont, Color watermarkTextColor, ImageCoordinate imageCoordinate) throws ImageException;
}
