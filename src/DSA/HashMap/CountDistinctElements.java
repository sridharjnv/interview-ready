package DSA.HashMap;

import java.util.HashSet;

public class CountDistinctElements {
    public static void main(String[] args){
        int[] nums = {10,20,10,30,20,40};
        System.out.println(countDistinct(nums));
    }
    static int countDistinct(int[] nums){
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
                set.add(num);
        }
        return set.size();
    }
}
