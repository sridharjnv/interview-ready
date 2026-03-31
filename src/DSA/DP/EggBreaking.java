package DSA.DP;

public class EggBreaking {
    public static void main(String[] args){
        int e = 2, f = 10;
        System.out.println(eggDrop(e, f));
    }
    static int eggDrop(int e, int f){
        int[][] dp = new int[e+1][f+1];

        for(int i=1;i<=e;i++){
            dp[i][0]=0;
            dp[i][1]=1;
        }

        for(int j=1;j<=f;j++){
            dp[1][j]=j;
        }

        for(int i=2;i<=e;i++){
            for(int j=2;j<=f;j++){
                dp[i][j]= Integer.MAX_VALUE;

                for(int k=1;k<=j;k++) {
                    int res = 1+Math.max(dp[i - 1][k - 1], dp[i][j - k]);
                    dp[i][j] = Math.min(dp[i][j], res);
                }
            }
        }
        return dp[e][f];
    }
}
