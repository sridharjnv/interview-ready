package DSA.Queue;

import java.util.LinkedList;
import java.util.Queue;

class StackWithQueue {
    Queue<Integer> q;

    StackWithQueue() {
        q = new LinkedList<>();
    }
    public void push(int x) {
        q.add(x);
        // Rotate previous elements
        int size = q.size();
        for(int i = 0; i < size - 1; i++){
            q.add(q.remove());
        }
    }
    public int pop() {
        if(q.isEmpty()){
            System.out.println("Stack Underflow");
            return -1;
        }
        return q.remove();
    }
    public int peek() {
        if(q.isEmpty()){
            return -1;
        }
        return q.peek();
    }

    public boolean isEmpty() {
        return q.isEmpty();
    }
}