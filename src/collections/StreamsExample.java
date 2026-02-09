package collections;

import java.util.ArrayList;
import java.util.List;

public class StreamsExample {

    public static void main(String[] args) {

        List<Integer> arrayList = new ArrayList<>();

        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);

        Object[] list1 = arrayList.stream().filter((Integer val) -> val %2 ==0).toArray();

        for(Object val : list1){
            System.out.println(val);
        }

    }
}
