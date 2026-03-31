package DSA.DP;

public class MaxSum {
    public static void main(String[] args) {
        int[] arr = {5, 5, 10, 100, 10, 5};
        System.out.println(maxSum(arr));
    }
    static int maxSum(int[] arr){
        int prev2 = 0;
        int prev1 =0;

        for(int num:arr){
            int pick = num+prev2;
            int curr = Math.max(pick, prev1);
            prev2=prev1;
            prev1=curr;
        }
        return prev1;
    }
}
