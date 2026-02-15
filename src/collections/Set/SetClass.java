package collections.Set;
import java.util.*;

public class SetClass {

    public static void main(String[] args){
        Set<Integer> set = new HashSet<>();

        set.add(10);
        set.add(3);
        boolean check = set.contains(10);
        set.remove(10);

        int size = set.size();


        //retain only intersection elements in lists
        List<Integer> l1 = new ArrayList<>(Arrays.asList(3,2,4,5,1,9,0));
        List<Integer> l2 = new ArrayList<>(Arrays.asList(3,2,4,5,1));

        Set<Integer> s1 = new HashSet<>(l1);

        Set<Integer> s2 = new HashSet<>(l2);

        s1.retainAll(s2);
//        System.out.println(s1);

        //union of 2 lists
        s1.addAll(s2);


    }
}
