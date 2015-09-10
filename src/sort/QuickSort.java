package sort;

public class QuickSort implements ISort {

	@Override
	public void sort(int[] data) {
		quickSort(data, 0, data.length - 1);
	}

	public void quickSort(int[] data, int p, int r) {
		if (p < r) {
			int q = partition(data, p, r);
			quickSort(data, p, q - 1);
			quickSort(data, q + 1, r);
		}
	}

	public int partition(int[] data, int p, int r) {
		int x = data[r];
		int i = p - 1;
		for (int j = p; j <= r; j++) {
			if (data[j] < x && i < j) {
				i++;
				int key = data[j];
				data[j] = data[i];
				data[i] = key;
				// if (i != j) {
				// data[i] += data[j];
				// data[j] = data[i] - data[j];
				// data[i] -= data[j];
				// }
			}
		}
		int key = x;
		data[r] = data[i + 1];
		data[i + 1] = key;

		// if ((i + 1) != r) {
		// data[i + 1] += data[r];
		// data[r] = data[i + 1] - data[r];
		// data[i + 1] -= data[r];
		// }

		return 1 + i;
	}

}
