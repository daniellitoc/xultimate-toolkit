package org.danielli.xultimate.test.thread.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import org.junit.Assert;
import org.junit.Test;

/**
 * 取自{@link http://www.iteye.com/topic/643724}
 */
public class CalculatorTest extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 8115854332749398604L;
	
	private static final int THRESHOLD = 100;
	private int start;
	private int end;

	public CalculatorTest(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int sum = 0;
		if ((start - end) < THRESHOLD) {
			for (int i = start; i < end; i++) {
				sum += i;
			}
		} else {
			int middle = (start + end) / 2;
			CalculatorTest left = new CalculatorTest(start, middle);
			CalculatorTest right = new CalculatorTest(middle + 1, end);
			left.fork();
			right.fork();

			sum = left.join() + right.join();
		}
		return sum;
	}
	
	@Test  
	public void run() throws Exception{  
	    ForkJoinPool forkJoinPool = new ForkJoinPool();  
	    Future<Integer> result = forkJoinPool.submit(new CalculatorTest(0, 10000));  
	  
	    Assert.assertEquals(new Integer(49995000), result.get());  
	}  
}
