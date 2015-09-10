package sort;

import data.DataFactory;

public class TestMain {

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		// int[] data = DataFactory.SORTDATA;
		double[] double_data = DataFactory.SORTDATADOUBLE;
		// ISort sort = InertSort.class.newInstance();
		// ISort sort = Bubblesort.class.newInstance();
		// ISort sort = Divide_Conquer_Combine.class.newInstance();
		// ISort sort = HeapSort.class.newInstance();
		// ISort sort = QuickSort.class.newInstance();
		// ISort sort = Divide.class.newInstance();
		// sort.sort(data);
		// for (int i : data) {
		// System.out.println(i);
		// }
		// CountSort countSort = new CountSort();
		// int[] result = countSort.sort(data, 1223);
		// for (int i : result) {
		// System.out.println(i + "--");
		// }
		BucketSort bucketSort = new BucketSort();
		double[] sort = bucketSort.sort(double_data);
		for (double d : sort) {
			System.out.println(d);
		}

	}

}
