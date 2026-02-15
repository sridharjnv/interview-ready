package collections.Queue;

import java.util.ArrayDeque;
import java.util.Queue;

public class QueueClass {
    public static void main(String[] args){
        Queue<Integer> queue = new ArrayDeque<>();

        queue.add(2);
        queue.add(1);
        queue.add(3);
        queue.offer(4);
        queue.offer(5);

        System.out.println(queue.peek());
        queue.remove();
        queue.poll();
        queue.poll();
        System.out.println(queue.peek());

    }
}
