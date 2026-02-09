package collections;

import java.util.ArrayList;
import java.util.List;

public class ArrayListClass {

    public static void main(String[] args) {

        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(0,100);
        arrayList.add(1,200);
        arrayList.add(2,300);


        List<Integer> arrayList2 = new ArrayList<>();
        arrayList2.add(0,400);
        arrayList2.add(1,500);
        arrayList2.add(2,600);

        arrayList.addAll(2,arrayList2);
        arrayList.forEach((Integer val) -> System.out.println(val));

        arrayList.replaceAll((Integer val) -> val * 2);

        System.out.println("element:" + arrayList.get(2));

        arrayList2.remove(2);

        arrayList.sort((Integer val1, Integer val2) -> val2 - val1);

        arrayList.set(2, 1200);
    }
}
