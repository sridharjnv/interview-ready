package DSA.Arrays;

import java.util.Map;
import java.util.TreeMap;

public class DuplicateCount {
    public static void main(String[] args) {
        int[] arr = {1,1,2,2,2,2,3,3,3,4,5,666,7,7,666,666};
        Map<Integer, Integer> result = new TreeMap<>();

        for(int num : arr){
            result.put(num, result.getOrDefault(num,0)+1);
        }
        System.out.println("Duplicate Counts: " + result);
    }
}
