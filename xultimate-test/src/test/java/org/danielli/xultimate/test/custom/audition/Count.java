package org.danielli.xultimate.test.custom.audition;

/**
 * 有一个整数n,写一个函数f(n),返回0到n之间出现的"1"的个数。比如f(13)=6,现在f(1)=1,找出小于11111111111的所有满足f(n)=n的n。
 */
public class Count {
	public static int calcOneCount(int n) {
		int number = 0;
		if (n > 9) {
   	 		if (n % 10 == 1) {
   	 			number++;
   	 		}
   	 		number += calcOneCount(n / 10);
   	 	} else if (n == 1) {
   	 		number++;
   	 	} 
		return number;
	}
	public static int calcTotalCount(int n) {
		int number = 0;
		for (int i = 0; i <= n; i++) {
			number += calcOneCount(i);
		}  
		return number;
	}

	public static void main(String[] args) {
		System.out.println(calcTotalCount(10000));
	}
}
