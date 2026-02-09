package collections;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class MaxPriorityQueueEx {

    public static void main(String[] args) {

        PriorityQueue<Integer> maxQu = new PriorityQueue<>((Integer a, Integer b) -> b-a);
        maxQu.add(5);
        maxQu.add(1);
        maxQu.add(2);
        maxQu.add(8);

        maxQu.forEach(System.out::println);
        while(!maxQu.isEmpty()){
            System.out.println("remove from top:" + maxQu.poll());
        }


        //thread safe PriorityBlockingQueue
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
        priorityBlockingQueue.add(4);
        priorityBlockingQueue.add(2);
        priorityBlockingQueue.add(7);

        System.out.println("element" + priorityBlockingQueue.peek());
    }
}
