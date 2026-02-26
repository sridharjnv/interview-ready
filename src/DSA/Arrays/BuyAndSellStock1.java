package DSA.Arrays;

public class BuyAndSellStock1 {
    public static void main(String[] args){
        int[] arr={1,5,2,8,10};
        System.out.println(maxProfit(arr));
    }
    public static int maxProfit(int[] arr){
        int n = arr.length;
        int profit =0;

        for(int i=1;i<n;i++){
            if(arr[i]>arr[i-1]){
                profit=profit+(arr[i]-arr[i-1]);
            }
        }
        return profit;

    }
}