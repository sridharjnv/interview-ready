package collections;

import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ArrayDequeClass {
    public static void main(String[] args) {

        ArrayDeque<Integer> arrayDequeAsQueue = new ArrayDeque<>();
        //insertion
        arrayDequeAsQueue.addLast(1);
        arrayDequeAsQueue.addLast(2);
        arrayDequeAsQueue.addLast(3);
        //deletion
        int element = arrayDequeAsQueue.removeFirst();
        System.out.println("Removed element: " + element);



        //deque as stack
        ArrayDeque<Integer> arrayDequeAsStack = new ArrayDeque<>();
        arrayDequeAsStack.push(2);
        arrayDequeAsStack.push(3);
        arrayDequeAsStack.push(4);

        int elementPopped = arrayDequeAsStack.pop();
        System.out.println("Popped element: " + elementPopped);


        ConcurrentLinkedDeque<Integer> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
        concurrentLinkedDeque.addLast(5);
        concurrentLinkedDeque.addLast(6);
        concurrentLinkedDeque.addLast(7);
        concurrentLinkedDeque.addFirst(8); //insert at front

        concurrentLinkedDeque.removeLast();
        concurrentLinkedDeque.peek();
    }
}
