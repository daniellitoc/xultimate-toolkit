package org.danielli.xultimate.test.thread;

import java.util.concurrent.SynchronousQueue;

/**
 * 沿一个方向传递。
 */
public class SynchronousQueueDemo {
	
	public static void main(String[] args) {
		SynchronousQueue<String> queue = new SynchronousQueue<String>();
		for (int i = 0; i < 3; i++)
			new Thread(new Producer(queue)).start();
		for (int i = 0; i < 2; i++)
			new Thread(new Consumer(queue)).start();
	}
	
	public static class Producer implements Runnable {
		
		private SynchronousQueue<String> queue;
		
		public Producer(SynchronousQueue<String> queue) {
			this.queue = queue;
		}

		public void run() {
			for (int i = 0; i < 2; i++) {
				try {
					queue.put(i + Thread.currentThread().getName());			// 会阻塞，直到消费者调用take()。
					Thread.sleep(1000 * 1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class Consumer implements Runnable {
		
		private SynchronousQueue<String> queue;
		
		public Consumer(SynchronousQueue<String> queue) {
			this.queue = queue;
		}

		public void run() {
			for (int i = 0; i < 3; i++) {
				try {
					String name = queue.take();		// 会阻塞，直到生产者调用put()。
					System.out.println(name);
					Thread.sleep(1000 * 1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			
			}
		}
	}
}


