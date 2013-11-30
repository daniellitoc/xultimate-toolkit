package org.danielli.xultimate.context.image;

import java.awt.Color;

/**
 * 抽像图片模板工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public abstract class AbstractImageTemplate {

	/** 背景颜色 */
	protected Color backgroundColor = Color.black;
	
	/** 目标图片品质(取值范围: 0 - 100) */
	protected Integer quality = 50;

	/**
	 * 设置背景颜色。
	 * @param backgroundColor
	 * 				背景颜色。
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * 设置目标图片品质(取值范围: 0 - 100)。
	 * @param backgroundColor
	 * 				目标图片品质(取值范围: 0 - 100)。
	 */
	public void setQuality(Integer quality) {
		this.quality = quality;
	}
}
