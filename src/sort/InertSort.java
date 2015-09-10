package sort;

/**
 * 对少量数据进行排序的有效算法 打扑克抓牌 o(n*n)
 * 
 * @author Think { 1, 6, 5, 4, 1222, 29, 872, 8 }
 */
public class InertSort implements ISort {

	@Override
	public void sort(int[] data) {
		for (int j = 1; j < data.length; j++) {
			int i = j - 1;
			int key = data[j];
			while (i >= 0 && data[i] > key) {
				data[i + 1] = data[i];
				data[i] = key;
				i -= 1;
			}

		}

	}
}
