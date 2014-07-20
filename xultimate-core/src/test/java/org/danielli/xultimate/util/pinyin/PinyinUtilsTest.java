package org.danielli.xultimate.util.pinyin;

import org.junit.Assert;
import org.junit.Test;

public class PinyinUtilsTest {

	@Test
	public void test() {
		Assert.assertNull(PinyinUtils.toHanyuPinyinStringArray('0', PinyinUtils.LOWER_WITHOUT_TONE_AND_WITH_V));
		Assert.assertNull(PinyinUtils.toHanyuPinyinStringArray('a', PinyinUtils.LOWER_WITHOUT_TONE_AND_WITH_V));
		String[] value = { "li" };
		Assert.assertEquals(value[0], PinyinUtils.toHanyuPinyinStringArray('李', PinyinUtils.LOWER_WITHOUT_TONE_AND_WITH_V)[0]);
		
		Assert.assertNull(PinyinUtils.getFirstHanyuPinyinString('0', PinyinUtils.LOWER_WITHOUT_TONE_AND_WITH_V));
		Assert.assertNull(PinyinUtils.getFirstHanyuPinyinString('a', PinyinUtils.LOWER_WITHOUT_TONE_AND_WITH_V));
		Assert.assertEquals("li", PinyinUtils.getFirstHanyuPinyinString('李', PinyinUtils.LOWER_WITHOUT_TONE_AND_WITH_V));
		
		Assert.assertEquals("li_0", PinyinUtils.toHanyuPinyinString("李0", PinyinUtils.LOWER_WITHOUT_TONE_AND_WITH_V, "_"));
		Assert.assertEquals("li_0a", PinyinUtils.toHanyuPinyinString("李0a", PinyinUtils.LOWER_WITHOUT_TONE_AND_WITH_V, "_"));
		Assert.assertEquals("li_0agong", PinyinUtils.toHanyuPinyinString("李0a工", PinyinUtils.LOWER_WITHOUT_TONE_AND_WITH_V, "_"));
		
		Assert.assertEquals("li0", PinyinUtils.toHanyuPinyinString("李0", PinyinUtils.LOWER_WITHOUT_TONE_AND_WITH_V, new PinyinCallback() {
			
			@Override
			public String call(String str, int index, char ch, String[] mainPinyinStrOfChars) {
				return mainPinyinStrOfChars[0];
			}
			
		}));
		Assert.assertEquals("li0a", PinyinUtils.toHanyuPinyinString("李0a", PinyinUtils.LOWER_WITHOUT_TONE_AND_WITH_V, new PinyinCallback() {
			
			@Override
			public String call(String str, int index, char ch, String[] mainPinyinStrOfChars) {
				return mainPinyinStrOfChars[0];
			}
			
		}));
		Assert.assertEquals("li0agong", PinyinUtils.toHanyuPinyinString("李0a工", PinyinUtils.LOWER_WITHOUT_TONE_AND_WITH_V, new PinyinCallback() {
			
			@Override
			public String call(String str, int index, char ch, String[] mainPinyinStrOfChars) {
				return mainPinyinStrOfChars[0];
			}
			
		}));
	}
}
