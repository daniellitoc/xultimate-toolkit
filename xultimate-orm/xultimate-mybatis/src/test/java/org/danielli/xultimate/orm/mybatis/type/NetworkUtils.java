package org.danielli.xultimate.orm.mybatis.type;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class NetworkUtils {
	
	public static String inet_ntoa(long add) {
		return ((add & 0xff000000) >> 24) + "." + ((add & 0xff0000) >> 16) + "." + ((add & 0xff00) >> 8) + "." + ((add & 0xff));
	}
	
	public static long inet_aton(String add) {
		long result = 0;
		// number between a dot
		long section = 0;
		// which digit in a number
		int times = 1;
		// which section
		int dots = 0;
		for (int i = add.length() - 1; i >= 0; --i) {
			if (add.charAt(i) == '.') {
				times = 1;
				section <<= dots * 8;
				result += section;
				section = 0;
				++dots;
			} else {
				section += (add.charAt(i) - '0') * times;
				times *= 10;
			}
		}
		section <<= dots * 8;
		result += section;
		return result;
	}
	
	public static long inet_aton(Inet4Address add) {
		byte[] bytes = add.getAddress();
		long result = 0;
		for (byte b : bytes) {
			if ((b & 0x80L) != 0) {
				result += 256L + b;
			} else {
				result += b;
			}
			result <<= 8;
		}
		result >>= 8;
		return result;
	}
	
	public static void main(String[] args) throws UnknownHostException {
		System.out.println(inet_aton("209.207.224.40"));
		System.out.println(inet_ntoa(3520061480L));
	}
}
