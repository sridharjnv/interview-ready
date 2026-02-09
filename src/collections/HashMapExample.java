package collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HashMapExample {
    public static void main(String[] args) {

        Map<Integer, String> rollNumberVsName = new HashMap<>();
        rollNumberVsName.put(null, "John Doe");
        rollNumberVsName.put(1, "Alice");
        rollNumberVsName.put(2, "Bob");
        rollNumberVsName.put(3,null);

        rollNumberVsName.putIfAbsent(null, "hello");
        rollNumberVsName.putIfAbsent(3, "haii");
        rollNumberVsName.putIfAbsent(4,"c");

        for(Map.Entry<Integer,String> entryMap: rollNumberVsName.entrySet()){
            Integer key = entryMap.getKey();
            String value = entryMap.getValue();
            System.out.println("Roll Number: " + key + ", Name: " + value);
        }

        rollNumberVsName.remove(3);

        for(Integer key: rollNumberVsName.keySet()){
            System.out.println("Roll Number: " + key + ", Name: " + rollNumberVsName.get(key));
        }


        Collection<String> values = rollNumberVsName.values();
        for(String value: values){
            System.out.println("Name: " + value);
        }
    }
}
