package org.danielli.xultimate.context.image2.config;

/**
 * 几何操作。
 * 
 * @author Daniel Li
 * @since JDK 1.7
 */
public enum GeometryOperator {
	/**
	 * 保留宽度或高度中的最小值，宽度与高度比保存。
	 */
	Minimum(null), 
	/**
	 * 保留宽度或高度中的最大值，宽度与高度比保存。
	 */
	Maximum('^'), 
	/**
	 * 保留宽度与高度，忽略原始宽度与高度。
	 */
	Emphasize('!'),
	/**
	 * 若宽度或高度小于原始宽度或高度，保留宽度或高度中的最小值，并缩小图片，宽度与高度比保存；否则使用原始宽度与高度。
	 */
	Shrink('>'),
	/**
	 * 若宽度或高度大于原始宽度或高度，保留宽度或高度中的最大值，并放大图片，宽度与高度比保存；否则使用原始宽度与高度。
	 */
	Enlarge('<');
	
	private Character special;
	
	private GeometryOperator(Character special) {
		this.special = special;
	}
	
	public Character getSpecial() {
		return special;
	}
}
