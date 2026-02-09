package DSA.Arrays;

public class LargestElementInArray {

    public static void main(String[] args) {
        int[] arr = {4,5,6,2,1,9};
        int n = arr.length;
        int max = arr[0];
        for(int i =0;i<=n-1;i++){
            if(arr[i]>max){
                max= i;
            }
        }
        System.out.println("largest element is at index:"+ max);
    }
}
