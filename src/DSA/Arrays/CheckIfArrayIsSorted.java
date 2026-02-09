package DSA.Arrays;

public class CheckIfArrayIsSorted {
    public static void main(String[] args){
        int[] arr = {0,893,4902};
        boolean isSorted = checkIfSorted(arr);

        System.out.println(isSorted);
    }

    public static boolean checkIfSorted(int[] arr){
        int n = arr.length;
        for(int i =1;i<n;i++){
            if(arr[i]<arr[i-1]) return false;
        }
        return true;
    }
}
