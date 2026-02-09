package DSA.Arrays;

public class MaxSumInCircularArray {
    public static void main(String[] args){
        int[] arr = {2,8,-1,7,3,-4,-5,1};
        System.out.println(maxSumInCircularSubArray(arr));
    }

    public static int maxSumInCircularSubArray(int[] arr){
        int n =arr.length;
        int max = kadaneMax(arr,n);
        if (max<0) return max;

        int totalSum=0;
        for(int i=0;i<n;i++){
            totalSum+=arr[i];
            arr[i]=-arr[i];
        }

        int min = kadaneMax(arr,n);
        int totalCircular = totalSum + min;

        return Math.max(max,totalCircular);
    }

    public static int kadaneMax(int[] arr, int n){

        int res=arr[0], current=arr[0];
        for(int i=1;i<n;i++){
            current=Math.max(current+arr[i],arr[i]);
            res=Math.max(res,current);
        }
        return res;
    }
}
