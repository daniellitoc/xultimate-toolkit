package org.danielli.xultimate.util.pinyin;

/**
 * 拼音回调接口。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 */
public interface PinyinCallback {
	
	/**
	 * 某个汉字的拼音处理。
	 * @param str 处理的字符串。
	 * @param index ch在str中所处的索引。
	 * @param ch 指定字符。
	 * @param mainPinyinStrOfChars 对应字符的拼音表示形式。
	 * @return 返回指定拼音。
	 */
	String call(String str, int index, char ch, String[] mainPinyinStrOfChars);
}
