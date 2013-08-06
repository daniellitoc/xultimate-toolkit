package org.danielli.xultimate.test.thread;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {
	public static void main(String args[]) {
		Exchanger<String> exgr = new Exchanger<String>();

		new UseString(exgr);
		new MakeString(exgr);
	}
}

class MakeString implements Runnable {
	Exchanger<String> ex;

	String str;

	MakeString(Exchanger<String> c) {
		ex = c;
		str = new String();

		new Thread(this).start();
	}

	public void run() {
		char ch = 'A';
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++)
				str += (char) ch++;

			try {
				str = ex.exchange(str);
			} catch (InterruptedException exc) {
				System.out.println(exc);
			}
		}
	}
}

class UseString implements Runnable {
	Exchanger<String> ex;

	String str;

	UseString(Exchanger<String> c) {
		ex = c;
		new Thread(this).start();
	}

	public void run() {

		for (int i = 0; i < 3; i++) {
			try {
				str = ex.exchange(new String());
				System.out.println("Got: " + str);
			} catch (InterruptedException exc) {
				System.out.println(exc);
			}
		}
	}
}
