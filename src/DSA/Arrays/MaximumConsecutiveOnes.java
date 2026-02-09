package DSA.Arrays;

public class MaximumConsecutiveOnes {
    public static void main(String[] args){
        int[] arr = {1,1,3,2,1,1,1,4,5,1,1,1,1,1};
        System.out.println(maximumOnes(arr));
    }
    public static int maximumOnes(int[] arr){
        int n = arr.length;
        int finalCount=0;
        int freq=0;
        for(int i=0;i<n;i++){
            if(arr[i] != 1){
                freq=0;
            } else {
                freq++;
                if(freq>finalCount){
                    finalCount=freq;
                }
            }
        }
        return finalCount;
    }
}
