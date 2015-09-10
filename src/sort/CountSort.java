package sort;

/**
 * 计数排序（前提条件，能够预先知道所需排序的上限，需要多余的一点空间） 适合数据密集。有明确范围的情况
 * 
 * @author Think
 * 
 */
public class CountSort {

	public int[] sort(int[] data, int max_limit) {
		int[] tmp = new int[max_limit];
		int[] des = new int[data.length]; 
		for (int i = 0; i < data.length; i++) {
			tmp[data[i]] += 1;
		}
		for (int j = 1; j < max_limit; j++) {
			tmp[j] += tmp[j - 1];
		}
		for (int k = data.length - 1; k >= 0; k--) {
			des[tmp[data[k]] - 1] = data[k];
			tmp[data[k]] -= 1;
		}

		return des;
	}

}
