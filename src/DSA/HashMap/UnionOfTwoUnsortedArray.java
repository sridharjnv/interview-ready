package DSA.HashMap;

import java.util.HashMap;
import java.util.HashSet;

public class UnionOfTwoUnsortedArray {
    public static void main(String[] args){
        int[] a = {10,12,15};
        int[] b = {18,12};
        System.out.println(findUnion(a,b));
    }
    static int findUnion(int[] a, int[] b){
        HashSet<Integer> set = new HashSet<>();
        for(int num:a) set.add(num);
        for(int num:b) set.add(num);
        return set.size();

    }
}
