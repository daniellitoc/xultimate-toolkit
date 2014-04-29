package org.danielli.xultimate.test.thread.forkjoin;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

/**
 * 取自{@link http://www.iteye.com/topic/643724}
 */
public class SortTaskTest extends RecursiveAction {
	private static final long serialVersionUID = -7181248569199273135L;
	
	final long[] array;
	final int start;
	final int end;
	private int THRESHOLD = 100; // For demo only

	public SortTaskTest(long[] array) {
		this.array = array;
		this.start = 0;
		this.end = array.length - 1;
	}

	public SortTaskTest(long[] array, int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}

	protected void compute() {
		if (end - start < THRESHOLD)
			sequentiallySort(array, start, end);
		else {
			int pivot = partition(array, start, end);
			new SortTaskTest(array, start, pivot - 1).fork();
			new SortTaskTest(array, pivot + 1, end).fork();
		}
	}

	private int partition(long[] array, int start, int end) {
		long x = array[end];
		int i = start - 1;
		for (int j = start; j < end; j++) {
			if (array[j] <= x) {
				i++;
				swap(array, i, j);
			}
		}
		swap(array, i + 1, end);
		return i + 1;
	}

	private void swap(long[] array, int i, int j) {
		if (i != j) {
			long temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
	}

	private void sequentiallySort(long[] array, int lo, int hi) {
		Arrays.sort(array, lo, hi + 1);
	}
	
	@Test  
	public void run() throws InterruptedException {  
	    ForkJoinPool forkJoinPool = new ForkJoinPool();  
	    Random rnd = new Random();  
	    long[] array = new long[50];  
	    for (int i = 0; i < array.length; i++) {  
	        array[i] = rnd.nextInt();  
	    }  
	    forkJoinPool.submit(new SortTaskTest(array));  
	  
	    forkJoinPool.shutdown();  
	    forkJoinPool.awaitTermination(1000, TimeUnit.SECONDS);  
	  
	    for (int i = 1; i < array.length; i++) {  
	    	Assert.assertTrue(array[i - 1] < array[i]);  
	    }  
	}  
}