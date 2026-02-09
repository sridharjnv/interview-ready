package MultiThreading;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {

    private Queue<Integer> queue;
    private int size;

    public Buffer(int size) {
        queue = new LinkedList<>();
        this.size = size;
    }

    public synchronized void produce(int item) throws Exception{

        while(queue.size() == size) {
            System.out.println("Buffer is full. Producer is waiting...");
            wait();
        }
        queue.add(item);
        System.out.println("Produced: " + item);
        notify();
    }

    public synchronized int consume() throws Exception{

        while(queue.isEmpty()) {
            System.out.println("Buffer is empty. Consumer is waiting...");
            wait();
        }
        int item = queue.poll();
        System.out.println("Consumed: " + item);
        notify();
        return item;
    }

}
