package org.danielli.xultimate.test.thread;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
	private int lockNum = 10;

    private Semaphore locks = new Semaphore(lockNum);

    public static void main(String[] args) {
        new SemaphoreDemo().testThread();
    }

    private void testThread() {

        for (int i = 1; i <= 20; i++) {
            try {
                System.out.println("第" + i + "个任务获取许可");
				locks.acquire();//从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。
                System.out.println("获取许可成功，剩余许可数:" + locks.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Process(locks, i).start();
            try {
                Thread.sleep(1000 * 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            System.out.println("释放许可数:" + locks.availablePermits());
            if (locks.availablePermits() == lockNum) {
                break;
            } else {
                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("执行结束。");
    }

    public class Process extends Thread {

        private Semaphore lock;

        private int i;

        public Process(Semaphore lock, int n) {
            this.lock = lock;
            i = n;
        }

        public void run() {
            try {
                Thread.sleep(1000 * 20);
                System.out.println("执行任务:" + i);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                lock.release();//释放一个许可，将其返回给信号量。
            }
        }
    }
}
