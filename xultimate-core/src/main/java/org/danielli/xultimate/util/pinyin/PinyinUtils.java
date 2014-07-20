package org.danielli.xultimate.util.pinyin;

import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * A class provides several utility functions to convert Chinese characters
 * (both Simplified and Tranditional) into various Chinese Romanization
 * representations
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 */
public class PinyinUtils {
	
	public static final HanyuPinyinOutputFormat LOWER_WITHOUT_TONE_AND_WITH_V= new HanyuPinyinOutputFormat();
	public static final HanyuPinyinOutputFormat UPPER_WITHOUT_TONE_AND_WITH_V= new HanyuPinyinOutputFormat();
	
	static {
		LOWER_WITHOUT_TONE_AND_WITH_V.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		LOWER_WITHOUT_TONE_AND_WITH_V.setVCharType(HanyuPinyinVCharType.WITH_V); 
		LOWER_WITHOUT_TONE_AND_WITH_V.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		
		UPPER_WITHOUT_TONE_AND_WITH_V.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		UPPER_WITHOUT_TONE_AND_WITH_V.setVCharType(HanyuPinyinVCharType.WITH_V); 
		UPPER_WITHOUT_TONE_AND_WITH_V.setCaseType(HanyuPinyinCaseType.UPPERCASE);
	}
	
	/**
	   * Get all Hanyu Pinyin presentations of a single Chinese character (both
	   * Simplified and Tranditional)
	   * 
	   * <p>
	   * For example, <br/> If the input is '间', the return will be an array with
	   * two Hanyu Pinyin strings: <br/> "jian1" <br/> "jian4" <br/> <br/> If the
	   * input is '李', the return will be an array with single Hanyu Pinyin
	   * string: <br/> "li3"
	   * 
	   * <p>
	   * <b>Special Note</b>: If the return is "none0", that means the input
	   * Chinese character is in Unicode CJK talbe, however, it has no
	   * pronounciation in Chinese
	   * 
	   * @param ch
	   *            the given Chinese character
	   * @param outputFormat
	   *            describes the desired format of returned Hanyu Pinyin String
	   * 
	   * @return a String array contains all Hanyu Pinyin presentations with tone
	   *         numbers; return null for non-Chinese character
	   * 
	   * @see HanyuPinyinOutputFormat
	   * @see BadHanyuPinyinOutputFormatCombination
	   * 
	   */
	public static String[] toHanyuPinyinStringArray(char ch, HanyuPinyinOutputFormat outputFormat) {
		try {
			return PinyinHelper.toHanyuPinyinStringArray(ch, outputFormat);
		} catch (Exception e) {
			return null;
		}
	}
	
	  /**
	   * Get the first Hanyu Pinyin of a Chinese character <b> This function will
	   * be removed in next release. </b>
	   * 
	   * @param ch
	   *            The given Unicode character
	   * @param outputFormat
	   *            Describes the desired format of returned Hanyu Pinyin string
	   * @return Return the first Hanyu Pinyin of given Chinese character; return
	   *         null if the input is not a Chinese character
	   * 
	   */
	 public static String getFirstHanyuPinyinString(char ch, HanyuPinyinOutputFormat outputFormat) {
	    String[] pinyinStrArray = toHanyuPinyinStringArray(ch, outputFormat);
	    if (ArrayUtils.isNotEmpty(pinyinStrArray)) {
	    	return pinyinStrArray[0];
	    } else {
	    	return null;
	    }
	  }
	 
	 /**
	   * Get a string which all Chinese characters are replaced by corresponding
	   * main (first) Hanyu Pinyin representation.
	   * 
	   * <p>
	   * <b>Special Note</b>: If the return contains "none0", that means that
	   * Chinese character is in Unicode CJK talbe, however, it has not
	   * pronounciation in Chinese. <b> This interface will be removed in next
	   * release. </b>
	   * 
	   * @param str
	   *            A given string contains Chinese characters
	   * @param outputFormat
	   *            Describes the desired format of returned Hanyu Pinyin string
	   * @param seperater
	   *            The string is appended after a Chinese character (excluding
	   *            the last Chinese character at the end of sentence). <b>Note!
	   *            Seperater will not appear after a non-Chinese character</b>
	   * @return a String identical to the original one but all recognizable
	   *         Chinese characters are converted into main (first) Hanyu Pinyin
	   *         representation
	   */
	public static String toHanyuPinyinString(String str, HanyuPinyinOutputFormat outputFormat, String seperater) {
		try {
			 StringBuilder resultPinyinStrBuf = new StringBuilder();

			 for (int i = 0; i < str.length(); i++) {
			      String pinyinStrOfChar = getFirstHanyuPinyinString(str.charAt(i), outputFormat);

			      if (StringUtils.isNotEmpty(pinyinStrOfChar)) {
			        resultPinyinStrBuf.append(pinyinStrOfChar);
			        if (i != str.length() - 1) { // avoid appending at the end
			          resultPinyinStrBuf.append(seperater);
			        }
			      } else {
			        resultPinyinStrBuf.append(str.charAt(i));
			      }
			 }
			 return resultPinyinStrBuf.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	   * Get a string which all Chinese characters are replaced by corresponding
	   * main (first) Hanyu Pinyin representation.
	   * 
	   * <p>
	   * <b>Special Note</b>: If the return contains "none0", that means that
	   * Chinese character is in Unicode CJK talbe, however, it has not
	   * pronounciation in Chinese. <b> This interface will be removed in next
	   * release. </b>
	   * 
	   * @param str
	   *            A given string contains Chinese characters
	   * @param outputFormat
	   *            Describes the desired format of returned Hanyu Pinyin string
	   * @param callback
	   * 			the callback of the array of char 
	   * @param seperater
	   *            The string is appended after a Chinese character (excluding
	   *            the last Chinese character at the end of sentence). <b>Note!
	   *            Seperater will not appear after a non-Chinese character</b>
	   * @return a String identical to the original one but all recognizable
	   *         Chinese characters are converted into main (first) Hanyu Pinyin
	   *         representation
	   */
	public static String toHanyuPinyinString(String str, HanyuPinyinOutputFormat outputFormat, PinyinCallback callback) {
		try {
			 StringBuilder resultPinyinStrBuf = new StringBuilder();

			 for (int i = 0; i < str.length(); i++) {
				  char ch = str.charAt(i);
			      String[] pinyinStrOfChars = toHanyuPinyinStringArray(ch, outputFormat);

			      if (ArrayUtils.isNotEmpty(pinyinStrOfChars)) {
			        resultPinyinStrBuf.append(callback.call(str, i, ch, pinyinStrOfChars));
			      } else {
			        resultPinyinStrBuf.append(ch);
			      }
			 }
			 return resultPinyinStrBuf.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
