package DSA.HashMap;

import java.util.HashSet;

public class SubArrayWithGivenSum {
    public static void main(String[] args){
        int[] a = {5,3,2,-1};
        int sum = 4;
        System.out.println(isSubArrSum(a,sum));
    }
    static boolean isSubArrSum(int[] a, int sum){
//        for(int i=0;i<a.length;i++){
//            int currSum=0;
//            for(int j=1;j<a.length;j++){
//                currSum+=a[j];
//                if(currSum==sum) return true;
//            }
//        }
        HashSet<Integer> set = new HashSet<>();
        int prefixSum=0;
        for (int j : a) {
            prefixSum += j;
            if (prefixSum == sum) return true;
            if (set.contains(prefixSum - sum)) return true;
            set.add(prefixSum);
        }
        return false;
    }
}
