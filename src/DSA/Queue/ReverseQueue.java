package DSA.Queue;

import java.util.LinkedList;
import java.util.Queue;

public class ReverseQueue {
    static void reverse(Queue<Integer> q){
        if(q.isEmpty())return;
        int x = q.remove();

        reverse(q);
        q.add(x);
    }

    public static void main(String[] args){
        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);

        reverse(q);
    }
}
