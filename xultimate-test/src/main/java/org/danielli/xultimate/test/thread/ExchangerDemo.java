package org.danielli.xultimate.test.thread;

import java.util.concurrent.Exchanger;

/**
 * 双向的交互。
 */
public class ExchangerDemo {
	public static void main(String args[]) {
		Exchanger<Integer> exchanger = new Exchanger<Integer>();
		
		new Thread(new Producer(exchanger)).start();
		
		try {
			Thread.sleep(1000 * 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		new Thread(new Consumer(exchanger)).start();
	}
	
	public static class Producer implements Runnable {
		private Exchanger<Integer> exchanger;

		public Producer(Exchanger<Integer> exchanger) {
			this.exchanger = exchanger;
		}

		public void run() {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 5; j++) {
					try {
						exchanger.exchange(i * 5 + j + 1);
						Integer result = exchanger.exchange(Integer.MAX_VALUE);
						System.out.println("Producer Get: " + result);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static class Consumer implements Runnable {
		private Exchanger<Integer> exchanger;

		public Consumer(Exchanger<Integer> exchanger) {
			this.exchanger = exchanger;
		}
		
		public void run() {

			for (int i = 0; i < 15; i++) {
				try {
					Integer result = exchanger.exchange(Integer.MAX_VALUE);
					System.out.println("Consumer Get: " + result);
					exchanger.exchange(result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}