package collections;

import java.util.PriorityQueue;

public class MinPriorityQueueEx {

    public static void main(String[] args) {

        PriorityQueue<Integer> minQu = new PriorityQueue<>();
        minQu.add(1);
        minQu.add(5);
        minQu.add(2);
        minQu.add(8);

        minQu.forEach(System.out::println);
         while(!minQu.isEmpty()){
             System.out.println("remove from top:" + minQu.poll());
         }

    }




}
