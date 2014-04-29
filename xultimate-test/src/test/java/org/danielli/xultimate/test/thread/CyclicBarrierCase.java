package org.danielli.xultimate.test.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * N个线程相互等待，任何一个线程完成之前，所有的线程都必须等待。像一个水闸，线程执行就想水流，在水闸处都会堵住，等到水满(线程到齐)了，才开始泄流。
 */
public class CyclicBarrierCase {
	
	// 徒步需要的时间
	private static int[] timeWalk = { 5, 8, 15 };

	// 自驾游
	private static int[] timeSelf = { 1, 3, 4 };

	// 旅游大巴
	private static int[] timeBus = { 2, 4, 6 };

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
				barrier.await();
				System.out.println(tourName + " 飞机 合肥");
			} catch (InterruptedException e) {
			} catch (BrokenBarrierException e) {
			}
		}
	}

	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(3);
		
		ExecutorService exec = Executors.newFixedThreadPool(3);
		
		exec.execute(new Tour(barrier, "徒步", timeWalk));
		exec.execute(new Tour(barrier, "自驾", timeSelf));
		exec.execute(new Tour(barrier, "大巴", timeBus));
		
		exec.shutdown();
	}
}
