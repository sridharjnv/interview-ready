package DSA.Arrays;

public class LeftRotateArrayByDElements {
    public static void main(String[] args){
        //if we call the leftRotateByOne d times is one solution

        int[] arr = {3,9,4,5,2};
        int n = arr.length;
        int d = 3;

        if (n==1) return;

        d =d%n;
        
        rotate(arr,0,d-1);
        rotate(arr,d,n-1);
        rotate(arr,0,n-1);

        for(int num: arr){
            System.out.println("Array:"+num);
        }

    }

    public static void rotate(int[] arr, int low , int high){
        while(low<high){
            int temp = arr[low];
            arr[low] = arr[high];
            arr[high] = temp;

            low++;
            high--;
        }
    }
}
