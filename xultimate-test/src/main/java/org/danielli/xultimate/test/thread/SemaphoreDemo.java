package org.danielli.xultimate.test.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 控制并发。
 */
public class SemaphoreDemo {
	private static final int lockNum = 10;

    public static void main(String[] args) {
    	
    	ExecutorService executorService = Executors.newFixedThreadPool(20);
    	
    	Semaphore locks = new Semaphore(lockNum);
    	
    	for (int i = 1; i <= 20; i++) {
            try {
                System.out.println("第" + i + "个任务获取许可");
				locks.acquire();//从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。
                System.out.println("获取许可成功，剩余许可数:" + locks.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.execute(new Task(locks, i));
        }
    	
    	
        while (true) {
            System.out.println("释放许可数:" + locks.availablePermits());
            if (locks.availablePermits() == lockNum) {
            	executorService.shutdown();
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

    public static class Task implements Runnable {
    	
    	private Semaphore semaphore;
    	
    	private int taskId;
    	
    	public Task(Semaphore semaphore, int taskId) {
			this.taskId = taskId;
			this.semaphore = semaphore;
		}
    	
    	@Override
    	public void run() {
    		 try {
                 Thread.sleep(1000 * 3);
                 System.out.println("执行任务:" + taskId);
             } catch (Exception ex) {
                 ex.printStackTrace();
             } finally {
            	 semaphore.release();//释放一个许可，将其返回给信号量。
             }
    	}
    }
}
