package DSA.DP;

public class SubSet {
    public static void main(String[] args){
        int[] arr = {2,3,7,8,10};
        int sum = 10;

        System.out.println(countSubSet(arr,sum));
    }

    public static int countSubSet(int[] arr, int sum){
        int n = arr.length;
        int[][] dp = new int[n+1][sum+1];

        for(int i=0;i<=n;i++){
            dp[i][0]= 1;
        }

        for(int i=1;i<=n;i++){
            for(int j=1;j<=sum;j++){
                if(arr[i-1]<=j){
                    dp[i][j]=dp[i-1][j]+dp[i-1][j-arr[i-1]];
                }
                else{
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        return dp[n][sum];
    }
}
