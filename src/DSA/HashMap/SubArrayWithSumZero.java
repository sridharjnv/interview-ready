package DSA.HashMap;

import java.util.HashSet;

public class SubArrayWithSumZero {
    public static void main(String[] args){
        int[] a = {-3,4,-3,-1,1};
        System.out.println(subArrayWithSumZero(a));
    }

    static boolean subArrayWithSumZero(int[] a){
        HashSet<Integer> set = new HashSet<>();
        int prefixSum=0;
        for (int j : a) {
            prefixSum += j;
            if (set.contains(prefixSum)) return true;
            if (prefixSum == 0) return true;
            set.add(prefixSum);
        }
        return false;
    }
}