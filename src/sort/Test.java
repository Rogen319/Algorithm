package sort;

public class Test {

	private static int dataLength = 0;

	public static void main(String[] args) {
		int[] data = {1,59999,2666,20,8,6,999,78,12,56,48};
		heapSort(data);
		for(Integer integer: data){
			System.out.print(integer + "	");
		}
	}

	private static void heapSort(int[] data) {
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

	public static void max_heapify(int[] data, int i) {
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
	 * 鏋勫缓鏈�澶у爢
	 * 
	 * @param data
	 */
	public static void buildMaxHeap(int[] data) {
		for (int j = ((dataLength - 1) / 2); j >= 0; j--) {
			max_heapify(data, j);
		}
	}
}
