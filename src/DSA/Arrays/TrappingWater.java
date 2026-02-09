package DSA.Arrays;

public class TrappingWater {
    public static void main(String[] args){
        int[] arr ={3,0,1,2,5};
        System.out.println(trappingWater(arr));
    }

    public static int trappingWater(int[] arr) {
        int n = arr.length;
        int l = 0;
        int r = n - 1;
        int leftMax = 0;
        int rightMax = 0;
        int water = 0;

        while(l<r){
            if(arr[l]<=arr[r]){
                if(arr[l]>=leftMax){
                    leftMax=arr[l];
                } else water =water+(leftMax-arr[l]);
                l++;
            }
            else {
                if(arr[r]>=rightMax){
                    rightMax=arr[r];
                } else water =water+(rightMax-arr[r]);
                r--;
            }
        }
        return water;
    }
}
