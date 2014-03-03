package org.danielli.xultimate.test.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * 一个线程(或者多个)，等待另外N个线程完成某个事情之后才能执行。是计数器，线程完成一个就记一个，就像报数，只不过是递减的。
 */
public class CountDownLatchDemo {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(4);

		CountDownLatch latch = new CountDownLatch(3);

		Worker w1 = new Worker(latch, "张三");
		Worker w2 = new Worker(latch, "李四");
		Worker w3 = new Worker(latch, "王二");

		Boss boss = new Boss(latch);

		executor.execute(w3);
		executor.execute(w2);
		executor.execute(w1);
		executor.execute(boss);

		executor.shutdown();
	}
	
	public static class Worker implements Runnable {
		private CountDownLatch downLatch;
		private String name;

		public Worker(CountDownLatch downLatch, String name) {
			this.downLatch = downLatch;
			this.name = name;
		}

		public void run() {
			this.doWork();
			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(10));
			} catch (InterruptedException ie) {
			}
			System.out.println(this.name + "活干完了！");
			this.downLatch.countDown();

		}

		private void doWork() {
			System.out.println(this.name + "正在干活!");
		}
	}
	
	public static class Boss implements Runnable {

		private CountDownLatch downLatch;

		public Boss(CountDownLatch downLatch) {
			this.downLatch = downLatch;
		}

		public void run() {
			System.out.println("老板正在等所有的工人干完活......");
			try {
				this.downLatch.await();
			} catch (InterruptedException e) {
			}
			System.out.println("工人活都干完了，老板开始检查了！");
		}
	}
}