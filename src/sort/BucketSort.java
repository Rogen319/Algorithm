package sort;

public class BucketSort {

	public double[] sort(double[] data) {
		return bucket_sort(data);
	}

	public double[] bucket_sort(double[] data) {
		double[] des = new double[data.length];
		Bucket[] tmp = new Bucket[10];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = new Bucket(0, null);
		}
		for (int i = 0; i < data.length; i++) {
			Bucket bucket = new Bucket(data[i], null);
			int bucket_list_index = (int) (data[i] * 10);
			bucket_in_sort(tmp[bucket_list_index], bucket);
		}
		int j = 0;
		for (int i = 0; i < tmp.length; i++) {
			Bucket tmp_bucket = tmp[i].next;
			while (tmp_bucket != null) {
				des[j] = tmp_bucket.value;
				tmp_bucket = tmp_bucket.next;
				j++;
			}
		}
		return des;
	}

	public void bucket_in_sort(Bucket sourct_bucket, Bucket bucket) {
		Bucket tmp = sourct_bucket.next;
		if (tmp == null) {
			sourct_bucket.next = bucket;
			return;
		}
		while (tmp.next != null) {
			if (tmp.value > bucket.value) {
				bucket.next = sourct_bucket.next;
				sourct_bucket.next = bucket;
				break;
			}
			tmp = tmp.next;
		}
		tmp.next = bucket;
	}

	public class Bucket {
		double value;
		public Bucket next;

		public Bucket(double value, Bucket bucket) {
			this.value = value;
			this.next = bucket;
		}

		public double getValue() {
			return value;
		}

		public void setValue(double value) {
			this.value = value;
		}

		public Bucket getBucketList() {
			return next;
		}

		public void setBucketList(Bucket next) {
			this.next = next;
		}

	}

}
