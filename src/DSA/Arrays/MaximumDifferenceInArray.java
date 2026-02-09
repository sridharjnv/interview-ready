package DSA.Arrays;

public class MaximumDifferenceInArray {

    // maximum difference arr[j] - arr[i] in array where j>i
   public static void main(String[] args){
       int[] arr = {4,9,2,3,10,1,4,6};
       int n = arr.length;

       System.out.println(maxDiff(arr,n));
   }

   public static int maxDiff(int[] arr, int n){
       if(n<2) return 0;

       int res = arr[1]-arr[0];
       int minValue = arr[0];

       for(int i=1;i<n;i++){
           if(res>arr[i]-minValue) res= arr[i]-minValue;
           if(arr[i]<minValue) minValue = arr[i];
       }
       return res;
   }
}
