package DSA.DP;

import java.util.Arrays;

public class LIS {
    public static void main(String[] args){
        int[] arr = {3,4,2,8,10,5,1};
        System.out.println(lis(arr));
    }
    static int lis(int[] arr){
        int n = arr.length;
        int[] dp = new int[n];

        Arrays.fill(dp, 1);
        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(arr[j]<arr[i]){
                    dp[i]=Math.max(dp[i],dp[j]+1);
                }
            }
        }
        int max =0;
        for(int val:dp){
            max = Math.max(max,val);
        }
        return max;
    }
}
