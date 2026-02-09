package DSA.Arrays;

public class RemoveDuplicatesFromSortedArray {
    public static void main(String[] args){
        int[] arr = {10,14,14,15,15,15};
        int n = arr.length;
        int res = 1;
        for(int i =1;i<n;i++){
            if(arr[i] != arr[res-1]){
                arr[res] = arr[i];
                res++;
            }
        }
            System.out.println(res);

    }
}
