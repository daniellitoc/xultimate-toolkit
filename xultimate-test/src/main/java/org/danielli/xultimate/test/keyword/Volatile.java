package org.danielli.xultimate.test.keyword;

/**
 * 测试volatile关键字
 */
public class Volatile {
	// 去除volatile关键字后，会导致修改后无法生效。
	private volatile boolean again = true;

	public void setAgain(boolean setAgain) {
		this.again = setAgain;
		System.out.println(this.again);
	}

	public void doWork() {
		while (again) {
		}
		System.out.println(System.currentTimeMillis());
	}

	public static void main(String[] args) {
		final Volatile vo = new Volatile();
		for (int i = 0; i < 10; i++) {
			Thread thread1 = new Thread(new Runnable() {
				@Override
				public void run() {
					vo.doWork();
				}
			});
			thread1.start();
		}
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				vo.setAgain(false);
			}
		});
		thread2.start();
	}
}
