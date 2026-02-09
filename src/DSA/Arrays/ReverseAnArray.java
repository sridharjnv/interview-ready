package DSA.Arrays;

public class ReverseAnArray {
    public static void main(String[] args){
        int[] arr = {10, 12, 14, 16, 18};
        int n = arr.length;
        int low = 0;
        int high = n-1;
        while(low<high){
            int temp = arr[low];
            arr[low] = arr[high];
            arr[high] = temp;
            low++;
            high--;
        }
        for(int i:arr) {
            System.out.println("Array: " + i);
        }
    }
}
