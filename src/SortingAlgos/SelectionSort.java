package SortingAlgos;

import java.util.Scanner;

public class SelectionSort {

    public static void selectionSort(int[] arr, int n ) {

        for(int i =0;i <n-1;i++){
            int minIndex = i;
            for(int j=i+1;j<n;j++){
                if(arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
        System.out.println("Sorted array: ");
        for(int i = 0;i<n;i++){
            System.out.print(arr[i] + " ");
        }
    }

    public static void main(String[] args) {

        System.out.println("Enter the size of the array:");
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();

        int[] arr = new int[size];
        System.out.println("Enter the elements in to the array:");
        for(int i =0;i<size;i++) {
            arr[i] = sc.nextInt();
        }
        sc.close();
        selectionSort(arr,size);
    }
}
