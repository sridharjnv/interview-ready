package DSA.HashMap;

import java.util.HashSet;

public class IntersectionOfTwoUnorderArrays {
    public static void main(String[] args){
        int[] a = {10,40,30,20};
        int[] b = {30,10};
        intersection(a,b);
    }
    static void intersection(int[] a, int[] b){
        HashSet<Integer> set = new HashSet<>();
        for(int num: a){
            set.add(num);
        }
        for(int num:b){
            if(set.contains(num)) System.out.println(num);
        }
    }
}
