package DSA.HashMap;

import java.util.HashMap;
import java.util.Map;

public class FrequenciesOfArrayElements {
    public static void main(String[] args){
        int[] nums = {1,2,1,1,3,3,4,2,5,1,4};
        frequencyCount(nums);
    }
    static void frequencyCount(int[] nums){
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int num:nums){
            map.put(num,map.getOrDefault(num,0)+1);
        }
        for(Map.Entry<Integer,Integer> e : map.entrySet()){
            System.out.println(e.getKey()+" "+e.getValue());
        }
    }
}
