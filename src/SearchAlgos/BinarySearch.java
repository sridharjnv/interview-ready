package SearchAlgos;

public class BinarySearch {

    public static int binarySearch(int[] arr , int target) {

        int left = 0;
        int right = arr.length-1;

        while(left <= right) {
            int mid = left +(right -left)/2;
            if(target == arr[mid]) return mid;
            else if(target < arr[mid]) right = mid-1;
            else left = mid+1;
        }
         return -1;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7};
        int target = 3;

        int result = binarySearch(nums, target);
        System.out.println("The target element is located in index: " +  result);
    }
}
// Time Complexity: O(log n)