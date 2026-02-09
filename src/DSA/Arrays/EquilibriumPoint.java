package DSA.Arrays;

public class EquilibriumPoint {
    public static void main(String[] args){
        int[] arr = {-7, 1, 5, 2, -4, 3, 0};
        System.out.println(checkEquilibriumPoint(arr));
    }

    public static int checkEquilibriumPoint(int[] arr){

        int n =arr.length;
        int totalSum=0;

        for(int i:arr){
            totalSum+=i;
        }

        int leftSum=0;
        for(int i=0;i<n;i++){
            totalSum-=arr[i];
            if(leftSum==totalSum){
                return i;
            }
            leftSum+=arr[i];
        }
        return -1;
    }
}
