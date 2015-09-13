package sort;

import java.awt.HeadlessException;

public class MyHeapSort {
	
	private static int heapSize;

	public static void main(String[] args) {
		int[] toSort = {1,59999,2666,20,8,6,999,78,12,56,48};
		heapSort(toSort);
		for(Integer integer: toSort){
			System.out.print(integer + "	");
		}
	}

	private static void heapSort(int[] toSort) {
		heapSize = toSort.length;
		buildMaxHeap(toSort);
		for(int i = heapSize - 1; i >= 1; i--){
			//System.out.println("The max value is : " + toSort[0] + " and the min value is: "
			//		+ toSort[i]);
			int max = toSort[0];
			toSort[0] = toSort[i];
			toSort[i] = max;
			heapSize--;
			maxHeapify(toSort, 0);
		}
	}

	private static void buildMaxHeap(int[] toSort) {
		for(int i = (heapSize - 1) / 2; i >= 0; i--)
			maxHeapify(toSort,i);
	}

	private static void maxHeapify(int[] toSort, int i) {
		int largest = i;
		int leftIndex = 2 * i + 1;
		int rightIndex = 2 * i + 2;
		if(leftIndex < heapSize && toSort[largest] < toSort[leftIndex])
			largest = leftIndex;
		if(rightIndex < heapSize && toSort[largest] < toSort[rightIndex])
			largest = rightIndex;
		if(largest != i){
			int temp = toSort[largest];
			toSort[largest] = toSort[i];
			toSort[i] = temp;
			maxHeapify(toSort, largest);
		}	
	}

}
