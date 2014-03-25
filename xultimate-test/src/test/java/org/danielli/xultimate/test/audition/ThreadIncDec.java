package org.danielli.xultimate.test.audition;

/**
 * 设计4个线程，其中两个线程每次增加1，另外两个线程每次减少1。
 */
public class ThreadIncDec {
	private int j;

	private synchronized void inc() {
		j++;
		System.out.println(Thread.currentThread().getName() + "-inc:" + j);
	}

	private synchronized void dec() {
		j--;
		System.out.println(Thread.currentThread().getName() + "-dec:" + j);
	}

	class Inc implements Runnable {
		public void run() {
			for (int i = 0; i < 100; i++) {
				inc();
			}
		}
	}

	class Dec implements Runnable {
		public void run() {
			for (int i = 0; i < 100; i++) {
				dec();
			}
		}
	}

	public static void main(String[] args) {
		ThreadIncDec incDec = new ThreadIncDec();
		Inc inc = incDec.new Inc();
		Dec dec = incDec.new Dec();
		for (int i = 0; i < 2; i++) {
			Thread t = new Thread(inc);
			t.start();
			t = new Thread(dec);
			t.start();
		}
	}
}
