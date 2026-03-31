package DSA.DP;

import java.util.Arrays;

public class MinCoins {
    public static void main(String[] args){
        int [] coins = {1,2,5};
        int v = 11;
        System.out.println(minCoins(coins,v));
    }
    static int minCoins(int[] coins, int v){
        int[] dp = new int[v+1];
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[0]=0;
        for(int i=0;i<=v;i++){
            for(int coin:coins){
                if(i-coin >=0 && dp[i-coin] != Integer.MIN_VALUE){
                    dp[i]=Math.min(dp[i],1+dp[i-coin]);
                }
            }
        }
        return dp[v]==Integer.MAX_VALUE ? -1: dp[v];
    }
}
