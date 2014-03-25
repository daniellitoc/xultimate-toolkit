package org.danielli.xultimate.test.audition;

import java.util.Arrays;

/**
 * 对[4，3，1，2]进行排序。
 */
public class QuickSort {
	public static <T> void swap(T[] theList, int i, int j) {
		T tempElement = theList[i];
		theList[i] = theList[j];
		theList[j] = tempElement;
	}

	private static <T extends Comparable<? super T>> int partition(T[] theList,
			int leftIndex, int rightIndex, int pivotIndex) {
		T pivotElement = theList[pivotIndex];
		swap(theList, pivotIndex, rightIndex);

		int newPivotIndex = leftIndex;
		for (int i = leftIndex; i < rightIndex; i++)
			if (theList[i].compareTo(pivotElement) < 0) {
				swap(theList, newPivotIndex, i);
				newPivotIndex++;
			}

		swap(theList, rightIndex, newPivotIndex);
		return newPivotIndex;
	}

	private static <T extends Comparable<? super T>> void recursiveSort(
			T[] theList, int leftIndex, int rightIndex) {
		if (rightIndex > leftIndex) {
			int newPivotIndex = partition(theList, leftIndex, rightIndex,
					leftIndex);
			recursiveSort(theList, leftIndex, newPivotIndex - 1);
			recursiveSort(theList, newPivotIndex + 1, rightIndex);
		}
	}

	public static <T extends Comparable<? super T>> void sort(T[] theList) {
		recursiveSort(theList, 0, theList.length - 1);
	}

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		Integer[] testArray = { 4, 3, 1, 2 };
		QuickSort.sort(testArray);
		System.out.println(Arrays.toString(testArray));
		System.out.println(System.nanoTime() - startTime);
	}
}
