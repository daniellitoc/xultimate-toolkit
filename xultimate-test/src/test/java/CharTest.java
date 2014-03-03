import java.lang.Character.UnicodeBlock;

import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.CharUtils;


public class CharTest {
	
	public static Character.UnicodeBlock[] CJK = {
    	Character.UnicodeBlock.CJK_COMPATIBILITY,
    	Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS,
    	Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS,
    	Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT,
    	Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT,
    	Character.UnicodeBlock.CJK_STROKES,
    	Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION,
    	Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS,
    	Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A,
    	Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B,
    	Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C,
    	Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D,
    	Character.UnicodeBlock.ENCLOSED_CJK_LETTERS_AND_MONTHS
    };
	
	public static UnicodeBlock[] LATIN = {
		UnicodeBlock.BASIC_LATIN,
		UnicodeBlock.LATIN_1_SUPPLEMENT,
		UnicodeBlock.LATIN_EXTENDED_A,
		UnicodeBlock.LATIN_EXTENDED_B
	};
    
	/**
	 * 拉丁文
	 * @param ub
	 * @return
	 */
	public static boolean isLatin(UnicodeBlock ub) {
		return ArrayUtils.contains(LATIN, ub);
	}
	
	/**
	 * 中日韩
	 * @param ub
	 * @return
	 */
    public static boolean isCJK(UnicodeBlock ub) {
    	return ArrayUtils.contains(CJK, ub);
    }
    
    /**
     * 全脚或半脚
     * @param ub
     * @return
     */
    public static boolean isHalfwidthAndfullwidthForms(UnicodeBlock ub) {
    	return ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }
    
    /**
     * 一般标点符号.
     * @param ub
     * @return
     */
    public static boolean isGeneralPunctuation(UnicodeBlock ub) {
    	return ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }
    
    public static UnicodeBlock of(int codePoint) {
    	return UnicodeBlock.of(codePoint);
    }
    
    public static boolean isCJK(int cp) {
    	Character.UnicodeBlock ub = Character.UnicodeBlock.of(cp);
    	return isCJK(ub) || isGeneralPunctuation(ub) || isHalfwidthAndfullwidthForms(ub);
    }
    
    public static long getLength(String value) {
    	int length = 1;
    	for (int i = 0; i < value.length(); i++) {
    		int cp = value.codePointAt(i);
    		if (Character.isSupplementaryCodePoint(cp)) {
    			i++;
    			length += 4;
    		} else {
    			if (CharUtils.isAsciiPrintable((char) cp)) {
    				length++;
    			} else {
    				length += 2;
    			}
    		}
    	}
    	return length / 2;
    }
}
