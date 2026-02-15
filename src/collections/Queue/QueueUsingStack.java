package collections.Queue;

import java.util.Stack;

public class QueueUsingStack {

    Stack<Integer> in = new Stack<>();
    Stack<Integer> out = new Stack<>();

    public void push(int x){
        in.push(x);
    }

    public int pop(){
        if(out.isEmpty()) {
            while(!in.isEmpty()) {
                out.push(in.pop());
            }
        }
        return out.pop();
    }

    public int peek(){
        if(out.isEmpty()) {
            while(!in.isEmpty()) {
                out.push(in.pop());
            }
        }
        return out.peek();
    }

    public static void main(String[] args){
        QueueUsingStack stack = new QueueUsingStack();
        stack.in.push(3);
        stack.in.push(4);
        stack.in.push(5);

        stack.in.pop();
        System.out.println(stack.in.peek());

    }
}
