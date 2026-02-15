package collections.Queue;

import java.util.LinkedList;
import java.util.Queue;

public class StackUsingQueue {

    static Queue<Integer> queue = new LinkedList<>();

    public void push(int x){
        queue.offer(x);

        for(int i=0;i< queue.size()-1;i++){
            queue.offer(queue.poll());
        }
    }

    public int poll(){
        return queue.poll();
    }

    public int peek(){
        return queue.peek();
    }
    public static void main(String[] args){

        StackUsingQueue queue = new StackUsingQueue();

        queue.push(3);
        queue.push(4);
        queue.push(5);

        System.out.println(queue.poll());

    }
}
