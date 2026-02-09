package DSA.Arrays;

public class MaximumSumInWindowSizeK {
    public static void main(String[] args) {
        int[] arr = {2, 3, -1, 4, 9, 0, 13};
        int k = 3;
        System.out.println(maximumSumInWindow(arr, k));
    }

    public static int maximumSumInWindow(int[] arr, int k){

        int n = arr.length;
        int curr = 0;
        for(int i=0;i<k;i++){
            curr +=arr[i];
        }
        int res = curr;

        for(int i =k;i<n;i++){
            int j = i-k;
            curr=curr + arr[i] - arr[j];
            res = Math.max(res,curr);
        }
        return res;
    }
}
