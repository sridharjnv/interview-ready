package DSA.Arrays;

public class MoveZeroesToEnd {
    public static void main(String[] args){
        int[] arr = {10,12,0,0,0,4,5,0,3};
        int n = arr.length;
        int count =0;
        for(int i=0;i<n;i++){
            if(arr[i] != 0){
                int temp = arr[i];
                arr[i] = arr[count];
                arr[count] = temp;
                count++;
            }
        }
        for(int num:arr){
            System.out.println("Array:"+ num);

        }
    }
}
