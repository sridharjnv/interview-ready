package DSA.Arrays;

public class SubArrayWithGivenSum {
    public static void main(String[] args){
        int[] arr = {0,4,3,0,0,4,2,2,1};
        int sum = 13;
        System.out.println(subArrayWithGivenSum(arr,sum));
    }

    public static boolean subArrayWithGivenSum(int[] arr, int sum){
        int curr = 0;
        int s=0;
        for (int i : arr) {
            curr += i;
            while (curr > sum) {
                curr -= arr[s];
                s++;
            }
            if (curr == sum) return true;
        }
        return false;
    }
}
