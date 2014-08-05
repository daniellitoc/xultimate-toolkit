package org.danielli.xultimate.test.thread.atom;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;

public class AtomicInteger {

	private java.util.concurrent.atomic.AtomicInteger atomicInteger;
	
	public AtomicInteger() {
		atomicInteger = new java.util.concurrent.atomic.AtomicInteger();
	}
	
	/**
     * Atomically increments by one the current value.
     *
     * @return the updated value
     */
    public final int incrementAndGet() {
        for (;;) {
            int current = atomicInteger.get();
            int next = current + 1;
            if (compareAndSet(current, next))
                return next;
        }
    }

    /**
     * Atomically decrements by one the current value.
     *
     * @return the updated value
     */
    public final int decrementAndGet() {
        for (;;) {
            int current = atomicInteger.get();
            int next = current - 1;
            if (compareAndSet(current, next))
                return next;
        }
    }
	
    /**
     * Atomically sets the value to the given updated value
     * if the current value {@code ==} the expected value.
     *
     * @param expect the expected value
     * @param update the new value
     * @return true if successful. False return indicates that
     * the actual value was not equal to the expected value.
     */
    public final boolean compareAndSet(int expect, int update) {
        if (atomicInteger.compareAndSet(expect, update)) 
        	return true;
        else {
        	LockSupport.parkNanos(100);
        	return false;
        }
    }
    
    @Test
    public void test() throws InterruptedException {
    	final AtomicInteger atomicInteger1 = new AtomicInteger();
    	final java.util.concurrent.atomic.AtomicInteger atomicInteger2 = new java.util.concurrent.atomic.AtomicInteger();
    	
    	ThreadPoolExecutor executor = new ThreadPoolExecutor(12, 12, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    	PerformanceMonitor.start("testAtomInteger");
    	testAtomInteger(atomicInteger1, executor);
    	PerformanceMonitor.mark("testAtomInteger1");
    	testAtomInteger(atomicInteger2, executor);
    	PerformanceMonitor.mark("testAtomInteger2");
    	
    	PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
    	
    	executor.shutdown();
    }
    
    // 使用了LockSupport.park，所以增加了上下文切换。不过减少了CAS操作不成功的概率。
    public static void testAtomInteger(final AtomicInteger atomicInteger, ThreadPoolExecutor executor) throws InterruptedException {
    	final CountDownLatch countDownLatch = new CountDownLatch(executor.getCorePoolSize());
    	for (int i = 0; i < executor.getCorePoolSize(); i++) {
    		executor.execute(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 50; i++) {
						for (int j = 0; j < 5000000; j++) {
							atomicInteger.incrementAndGet();
						}
					}
					countDownLatch.countDown();
				}
			});
    	}
    	countDownLatch.await();
    }
    
    // US虽然100%，上下文切换很少，但是这个时候全部线程都处于无限循环，CAS操作更难成功，所以按完成时间比较长。
    public static void testAtomInteger(final java.util.concurrent.atomic.AtomicInteger atomicInteger, ThreadPoolExecutor executor) throws InterruptedException {
    	final CountDownLatch countDownLatch = new CountDownLatch(executor.getCorePoolSize());
    	for (int i = 0; i < executor.getCorePoolSize(); i++) {
    		executor.execute(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 50; i++) {
						for (int j = 0; j < 5000000; j++) {
							atomicInteger.incrementAndGet();
						}
					}
					countDownLatch.countDown();
				}
			});
    	}
    	countDownLatch.await();
    }
}
