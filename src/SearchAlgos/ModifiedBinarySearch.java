package SearchAlgos;

public class ModifiedBinarySearch {

    public static int modifiedBinarySearch(int[] arr, int target) {

        int left =0;
        int right = arr.length-1;
        while(left<=right) {
            int mid = left + (right -left)/2;
            if(target ==arr[mid]) return mid;

            if(arr[left] <= arr[mid]) {
                if(target >=arr[left] && target < arr[mid]) right = mid-1;
                else left = mid+1;
            }

            else {
                if(target >arr[mid] && target <= arr[right]) left = mid+1;
                else right = mid-1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {3,4,5,6,7,0,1,2};
        int target = 0;
        int result = modifiedBinarySearch(nums, target);
        System .out.println("The target element is located at : "+ result);
    }
}

// Time Complexity : O(log n)
