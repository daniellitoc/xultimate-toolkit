package org.danielli.xultimate.test.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierDemo {
	// 徒步需要的时间
	private static int[] timeWalk = { 5, 8, 15, 15, 10 };

	// 自驾游
	private static int[] timeSelf = { 1, 3, 4, 4, 5 };

	// 旅游大巴
	private static int[] timeBus = { 2, 4, 6, 6, 7 };

	static String now() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date()) + ": ";
	}

	static class Tour implements Runnable {
		private int[] times;

		private CyclicBarrier barrier;

		private String tourName;

		public Tour(CyclicBarrier barrier, String tourName, int[] times) {
			this.times = times;
			this.tourName = tourName;
			this.barrier = barrier;
		}

		public void run() {
			try {
				Thread.sleep(times[0] * 1000);
				System.out.println(now() + tourName + " 合肥");
				Thread.sleep(times[1] * 1000);
				System.out.println(now() + tourName + " 南京");
				Thread.sleep(times[2] * 1000);
				System.out.println(now() + tourName + " 无锡");
				Thread.sleep(times[3] * 1000);
				System.out.println(now() + tourName + " 苏州");
				Thread.sleep(times[4] * 1000);
				System.out.println(now() + tourName + " 上海");
				barrier.await();
				System.out.println(tourName + "飞机 合肥");
			} catch (InterruptedException e) {
			} catch (BrokenBarrierException e) {
			}
		}
	}

	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(3);
		ExecutorService exec = Executors.newFixedThreadPool(3);
		exec.submit(new Tour(barrier, "徒步", timeWalk));
		exec.submit(new Tour(barrier, "自驾", timeSelf));
		exec.submit(new Tour(barrier, "大巴", timeBus));
		exec.shutdown();
	}
}
