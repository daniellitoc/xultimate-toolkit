package org.danielli.xultimate.web.util;

import org.danielli.xultimate.util.crypto.DigestUtils;
import org.danielli.xultimate.util.crypto.MessageDigestAlgorithms;

public class ShortURLGenerator {

	private String[] chars = new String[] { // 要使用生成URL的字符
	"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
			"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1",
			"2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };

	public String[] createPossibleUrl(String sourceUrl) {
		String hex = DigestUtils.digest(MessageDigestAlgorithms.MD5, sourceUrl);

		String[] shortUrls = new String[4];
		for (int i = 0; i < shortUrls.length; i++) {
			StringBuilder urlBuilder = new StringBuilder();
			int j = i + 1;
			String subHex = hex.substring(i * 8, j * 8);
			long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);

			for (int k = 0; k < 6; k++) {
				int index = (int) (Long.valueOf("0000003D", 16) & idx);
				urlBuilder.append(chars[index]);
				idx = idx >> 5;
			}
			shortUrls[i] = urlBuilder.toString();
		}
		return shortUrls;
	}
}
