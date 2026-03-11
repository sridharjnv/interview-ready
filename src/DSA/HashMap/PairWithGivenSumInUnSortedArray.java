package DSA.HashMap;

import java.util.HashSet;

public class PairWithGivenSumInUnSortedArray {
    public static void main(String[] args){
        int[] a = {8,3,4,2,5};
        int sum =6;
        System.out.println(pairWithSum(a,sum));
    }
    static boolean pairWithSum(int[] a,int sum){
        HashSet<Integer> set = new HashSet<>();
        for(int x:a){
            if(set.contains(sum-x)) return true;
            else set.add(x);
        }
        return false;
    }
}
