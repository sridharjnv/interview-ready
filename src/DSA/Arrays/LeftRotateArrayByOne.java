package DSA.Arrays;

public class LeftRotateArrayByOne {
    public static void main(String[] args){
        int[] arr ={10,12,13,14,15};
        int n = arr.length;
        int first = arr[0];
        for(int i=1;i<n;i++){
            arr[i-1]=arr[i];
        }
        arr[n-1] = first;
        for(int num:arr){
            System.out.println("Array: "+num);
        }
    }
}
