package sort;

public class InsertSort {

	public static void main(String[] args) {

		int[] toSort = {20,8,6,999,78,12,56,48};
		//insertSort(toSort);
		bubbleSort(toSort);
		//improvedBubbleSort(toSort);
		for(Integer integer: toSort){
			System.out.print(integer + "	");
		}
	}

	//�Ľ���ð������
	private static void improvedBubbleSort(int[] toSort) {
		boolean flag = true;
		for(int i = 0; i < toSort.length - 1; i++){
			if(flag == false){
				return;
			}
			flag = false;
			for(int j = (toSort.length - 1); j >= i + 1; j--){
				if(toSort[j] < toSort[j - 1]){
					int temp = toSort[j];
					toSort[j] = toSort[j - 1];
					toSort[j - 1] = temp;
					flag = true;
				}
			}
		}		
	}

	//ð������
	private static void bubbleSort(int[] toSort) {
		for(int i = 0; i < toSort.length - 1; i++){
			for(int j = (toSort.length - 1); j >= i + 1; j--){
				if(toSort[j] < toSort[j - 1]){
					int temp = toSort[j];
					toSort[j] = toSort[j - 1];
					toSort[j - 1] = temp;
				}
			}
		}
	}

	//��������
	private static void insertSort(int[] toSort) {

		for(int i = 1; i < toSort.length; i++){
			int temp = toSort[i];
			int index = i;
			for(int j = i - 1; j >= 0; j--){
				if(toSort[j] > temp){
					toSort[j + 1] = toSort[j];
					index = j;
				}
			}
			toSort[index] = temp;
		}
	}

}
