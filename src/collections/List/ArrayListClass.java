package collections.List;

import java.util.*;

public class ArrayListClass {
    public static void main(String[] args){

        List<Integer> l1 = new ArrayList<>(Arrays.asList(8,2,9,4,2,0));


        // sum of list
        int sum = l1.stream().mapToInt(n->n).sum();

        //max
        Collections.max(l1);
        //min
        Collections.min(l1);

        //count even numbers
        long evenCount = l1.stream().filter(n -> n%2==0).count();

        //remove duplicates from list
        Set<Integer> set = new HashSet<>(l1);
        List<Integer> uniqueValues = l1.stream().distinct().toList();

        //reverse a list
        Collections.reverse(l1);

        //Second Largest
        int num = l1.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .orElse(-1);

        //find squares
        List<Integer> squares = l1.stream().map(n-> n*n).toList();
        //System.out.println(squares);

        //filter numbers greater than 50
        List<Integer> numbersGreaterThan50 = l1.stream().filter(n->n>50).toList();
//        System.out.println(numbersGreaterThan50);

        //sort list descending
        Collections.sort(l1,Comparator.reverseOrder());

        //find average of list
        Double average = l1.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(-1);

        //get top 3 largest elements
        List<Integer> topThreeLargestElements = l1.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .toList();
        //        System.out.println(topThreeLargestElements);







    }
}
