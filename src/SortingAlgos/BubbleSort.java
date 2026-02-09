package SortingAlgos;

import java.util.Scanner;

public class BubbleSort {

    public static void bubbleSort(int[] arr, int n) {
        for(int i=0;i<n-1;i++) {
            boolean swap=false;
            for(int j =0;j<n-i-1;j++){
                if(arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
                swap =true;
            }
            if(!swap) break;

        }
        System.out.print("Sorted array: ");
        for(int i : arr) {
            System.out.print(i+ " ");
        }
    }



    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of the array:");
        int size = sc.nextInt();
        int[] arr = new int[size];

        System.out.println("Enter the elements in to the array: ");

        for(int i =0;i<size;i++) {
            arr[i] = sc.nextInt();
        }

        sc.close();
        bubbleSort(arr,size);
    }
}

// Time Complexity: O(n^2)
