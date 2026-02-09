package DSA.Arrays;

public class MaxLenEvenOddSubArray {
    public static void main(String[] args){
        int[] arr ={7,8,4,2,1,0};
        System.out.println(maxLenEvenOddSubArray(arr));
    }
    public static int maxLenEvenOddSubArray(int[] arr){
        int n = arr.length;
        int res=1;
        int current=1;
        for(int i=1;i<n;i++){
            if((arr[i]%2==0 && arr[i-1]%2 != 0) || (arr[i]%2!=0 && arr[i-1]%2 ==0)){
                current++;
                res=Math.max(res,current);
            }
            else current=1;
        }
        return res;
    }
}
