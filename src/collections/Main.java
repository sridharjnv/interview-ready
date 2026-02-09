package collections;
import java.util.*;

import static java.util.Collections.min;

public class Main {

    public static void main(String[] args) {

        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);

        //using iterator
        System.out.println("Iterating the values using iterator method");
        Iterator<Integer> valuesIterator = values.iterator();
        while(valuesIterator.hasNext()) {
            int val = valuesIterator.next();
            System.out.println(val);
            if(val == 3) {
                valuesIterator.remove();
            }
        }

        //using for loop to iterate over the list
        System.out.println("Iterating using for loop");
        for(int val : values) {
            System.out.println(val);
        }

        //using for each loop to iterate over the list
        System.out.println("Iterating using for each loop");
        values.forEach((Integer val) -> System.out.println(val));

        //size
        System.out.println("Size of the list: " + values.size());

        //isEmpty
        System.out.println("Is the list empty? " + values.isEmpty());

        //contains
        System.out.print("Is the list contains 3? " + values.contains(3));

        //add
        values.add(5);
        System.out.print("Adding 5 to the list: ");

        //remove using index
        values.remove(3);

        //remove using object
        values.remove(Integer.valueOf(3));

        Stack<Integer> stackValues = new Stack<>();
        stackValues.add(6);
        stackValues.add(7);
        stackValues.add(8);

        //addAll
        values.addAll(stackValues);

        //removeAll
        values.removeAll(stackValues);

        //clear
        values.clear();

        System.out.println("Is the values is Empty" + values.isEmpty());

        Collections.sort(values);
        Collections.min(values);
        Collections.max(values);
        Collections.reverse(values);
        Collections.shuffle(values);
        Collections.binarySearch(values, 5);
        Collections.copy(stackValues, values);




    }


}
