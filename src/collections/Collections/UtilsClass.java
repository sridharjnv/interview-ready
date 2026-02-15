package collections.Collections;
import java.util.*;

public class UtilsClass {
    public static void main(String[] args){
        List<Integer> list = new ArrayList<>(Arrays.asList(2,3,4,5,5,6));

        list.add(6);
        list.add(5);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        for(int i : list){
            System.out.println(i);
        }

        Collections.sort(list);
//        list.stream().map(x-> System.out.println(x));

        Collections.sort(list, Comparator.reverseOrder());

        Collections.reverse(list);

        Collections.max(list);
        Collections.min(list);

        Set<Integer> set = new HashSet<>(list);


    }
}
