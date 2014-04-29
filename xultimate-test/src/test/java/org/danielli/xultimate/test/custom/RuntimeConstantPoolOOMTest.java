package org.danielli.xultimate.test.custom;

public class RuntimeConstantPoolOOMTest {

	public static void main(String[] args) {
		// "I'm"和"Daniel Li"在之前都没出现过，现在都在常量池中指向了对象的引用。
		String abc1 = new StringBuilder("I'm ").append("Daniel Li").toString();
		abc1.intern();	// "I'm Daniel Li"之前也没出现过，现在常量池中指向了abc1的引用。
		String abc2 = "I'm Daniel Li"; // "I'm Daniel Li"之前出现过，现在常量池中指向了abc1的引用。
		System.out.println(abc1 == abc2);	// true
		
		String val2 = "I'm not Daniel Li"; // "I'm not Daniel Li"之前没出现过，现在常量池中指向了val2的引用。
		// "not Daniel Li"在之前都没出现过，现在都在常量池中指向了对象的引用。
		String val1 = new StringBuilder("I'm ").append("not Daniel Li").toString();
		System.out.println(val1.intern() == val1);		// false。"I'm not Daniel Li"之前出现过，val1.intern()只想的是val2。
		System.out.println(val1.intern() == val2);		// true
		System.out.println(val2 == val1);				// false
	}
}
