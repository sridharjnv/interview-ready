package DSA.DP;

public class PalindromePartition {
    public static void main(String[] args) {
        String s = "aab";
        System.out.println(minCuts(s));
    }

    static int minCuts(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];
        // precompute palindrome
        for(int gap = 0; gap < n; gap++) {
            for(int i = 0, j = gap; j < n; i++, j++) {
                if(gap == 0) {
                    isPal[i][j] = true;
                }
                else if(gap == 1) {
                    isPal[i][j] = (s.charAt(i) == s.charAt(j));
                }
                else {
                    isPal[i][j] = (s.charAt(i) == s.charAt(j)) && isPal[i+1][j-1];
                }
            }
        }

        int[] dp = new int[n];
        for(int i = 0; i < n; i++) {
            if(isPal[0][i]) {
                dp[i] = 0;
            } else {
                dp[i] = Integer.MAX_VALUE;
                for(int j = 0; j < i; j++) {
                    if(isPal[j+1][i]) {
                        dp[i] = Math.min(dp[i], 1 + dp[j]);
                    }
                }
            }
        }
        return dp[n - 1];
    }
}
