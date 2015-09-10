package sort;

/**
 * 冒泡排序
 * 
 * @author Think
 * 
 */
public class Bubblesort implements ISort {

	@Override
	public void sort(int[] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = data.length - 1; j > i + 1; j--) {
				if (data[j - 1] > data[j]) {
					data[j - 1] = data[j - 1] + data[j];
					data[j] = data[j - 1] - data[j];
					data[j - 1] -= data[j];
				}
			}
		}
	}

}
