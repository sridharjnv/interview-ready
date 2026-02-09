package DSA.Arrays;

public class MaxSubArray {
    public static void main(String[] args){
        int[] arr= {-2,1};
        System.out.println(maxSubArray(arr));
    }

    public static int maxSubArray(int[] arr){
        int n = arr.length;

        int res=arr[0];
        int maxEnding=arr[0];
        for(int i=1;i<n;i++){
            maxEnding=Math.max(arr[i]+maxEnding,arr[i]);
            res=Math.max(res,maxEnding);
        }
        return res;
    }
}
