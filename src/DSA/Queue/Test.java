package DSA.Queue;

import java.util.ArrayDeque;
import java.util.Queue;

public class Test {
    public static void main(String[] args){
        Queue<Integer> a = new ArrayDeque<>();

        a.offer(10);
        a.offer(20);
        a.offer(30);
        System.out.println(a.size());
        System.out.println(a.isEmpty());
        System.out.println(a);
    }
}
