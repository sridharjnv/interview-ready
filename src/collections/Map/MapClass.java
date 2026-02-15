package collections.Map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapClass {
    public static void main(String[] args){
        Map<Integer,String> map = new HashMap<>();

        map.put(1,"Apple");
        map.put(2,"Samsung");

        map.get(1);

        map.containsKey(2);

        System.out.println(map.size());

        for(Integer k:map.keySet()){
            System.out.println(map.get(k));
        }

        for(String s:map.values()){
            System.out.println(s);
        }

        for(Map.Entry<Integer,String> e: map.entrySet()){
            System.out.println(e.getKey()+"->"+e.getValue());
        }

        //frequency of characters in a string
        String s = "This is a beautiful place";

        Map<Character,Integer> map1 = new HashMap<>(26);
        for(char ch:s.toCharArray()){
            if(map1.containsKey(ch)){
                map1.put(ch, map1.get(ch)+1);
            }
            else {
                map1.put(ch,1);
            }
        }

        for(Map.Entry<Character,Integer> e : map1.entrySet()){
            System.out.println(e.getKey()+"->"+e.getValue());
        }

        //frequency of words in a string
        String normalString = "backflipt backflipt is is a great place";

        String[] words = normalString.strip().split("\\s");

        Map<String,Integer> map2 = new HashMap<>(words.length);

        for(String i: words){
            if(map2.containsKey(i)){
                map2.put(i, map2.get(i)+1 );
            }
            else{
                map2.put(i,1);
            }
        }
        for(Map.Entry<String,Integer> i: map2.entrySet()){
            System.out.println(i.getKey()+" "+i.getValue());
        }

        map2.entrySet().stream()
                .sorted(Map.Entry.comparingByValue());

        //find majority

        int[] nums = {2,3,4,1,1,2,1,1,1};
        Map<Integer,Integer> map3 = new HashMap<>();

        for(int i :nums){
            map3.put(i,map3.getOrDefault(i,0)+1);
        }


    }
}
