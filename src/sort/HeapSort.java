package sort;

/**
 * O(nlgn) 把数据假象构造成一个二叉树，每个节点的左右坐标跟别为2i,2i+1. 构造Max树：
 * 
 * @author Think
 * 
 */
public class HeapSort implements ISort {
	int dataLength = 0;

	/***
	 * 将最大的值拿出来，放到最后，然后通过1--n-1个数据，找新的最大数
	 */
	@Override
	public void sort(int[] data) {
		dataLength = data.length;
		buildMaxHeap(data);
		for (int i = dataLength - 1; i >= 1; i--) {
			data[i] += data[0];
			data[0] = data[i] - data[0];
			data[i] -= data[0];
			dataLength--;
			max_heapify(data, 0);
		}
	}

	/**
	 * 保证二叉堆的性质 A[i] >= A[left[i]] A[i] >= A[right[i]]
	 * 在构造二叉堆和每次从最后移除一个元素以后都要重新组织二叉堆的结构
	 * 
	 * @param data
	 * @param i
	 */
	public void max_heapify(int[] data, int i) {
		int largest = 0;
		int l = 2 * i + 1;
		int r = 2 * (i + 1);
		if (l < dataLength && data[l] > data[i]) {
			largest = l;
		} else {
			largest = i;
		}
		if (r < dataLength && data[r] > data[largest]) {
			largest = r;
		}
		if (largest != i) {
			data[i] += data[largest];
			data[largest] = data[i] - data[largest];
			data[i] -= data[largest];
			max_heapify(data, largest);
		}
	}

	/**
	 * 构建最大堆
	 * 
	 * @param data
	 */
	public void buildMaxHeap(int[] data) {
		for (int j = ((dataLength - 1) / 2); j >= 0; j--) {
			max_heapify(data, j);
		}
	}
}
