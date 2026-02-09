package DSA.Stack;

import java.util.Stack;

public class MinStack {
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> minStack = new Stack<>();

    public void push(int x){
        stack.push(x);
        if(minStack.isEmpty() || x <= minStack.peek()){
            minStack.push(x);
        }
    }

    public int pop(){
        if (stack.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        int popped = stack.pop();
        if(popped == minStack.peek()){
            minStack.pop();
        }
        return popped;
    }

    public int peek() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return stack.peek();
    }

    public int getMin(){
        if(minStack.isEmpty()) return -1;
        else return minStack.peek();
    }
    public static void main(String[] args){

        MinStack ms = new MinStack();
        ms.push(5);
        ms.push(3);
        ms.push(7);
        ms.push(3);

        System.out.println("Min: " + ms.getMin()); // 3
        ms.pop();
        System.out.println("Min after pop: " + ms.getMin()); // 3
        ms.pop();
        System.out.println("Min after pop: " + ms.getMin()); // 3
        ms.pop();
        System.out.println("Min after pop: " + ms.getMin()); // 5
    }

}
