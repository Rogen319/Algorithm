package sort;

/***
 * 分治发 分解--解决--合并 O(nlgn)
 * 
 * @author Think
 * 
 */
public class Divide implements ISort {

	@Override
	public void sort(int[] data) {
		merge_sort(data, 0, data.length);
	}

	public void merge_sort(int[] data, int begin, int end) {
		int tag = (begin + end) / 2;
		merge(data, begin, tag, end);
	}

	public void merge(int[] data, int p, int q, int r) {
		int n1 = q - p;
		int n2 = r - q;
		int[] arr1 = new int[n1];
		int[] arr2 = new int[n2];

		for (int i = 0; i < n1; i++) {
			arr1[i] = data[p + i];
		}

		for (int j = 0; j < n2; j++) {
			arr2[j] = data[q + j];
		}

		int i = 0;
		int j = 0;
		for (int k = p; k < r; k++) {
			if (i < n1 && arr1[i] <= arr2[j]) {
				data[k] = arr1[i];
				i += 1;
			} else if (j < n2) {
				data[k] = arr2[j];
				j += 1;
			}
		}
	}
}
