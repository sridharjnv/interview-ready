package SortingAlgos;

public class InsertionSort {

    public static void insertionSort(int[] arr) {

        for(int i = 1;i<arr.length;i++) {
            int key = arr[i];
            int j = i-1;
            while(j>=0 && arr[j] > key){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;
        }
    }

    public static void main(String[] args) {
        int[] arr = {8,6,1,3,5,4};
        insertionSort(arr);

        System.out.print("Sorted array: ");
        for(int num: arr){
            System.out.print(num + " ");
        }
    }
}
//Time Complexity: O(n^2)