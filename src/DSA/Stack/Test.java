package DSA.Stack;

public class Test {
    static class MyStack{
        int cap;
        int top;
        int[] arr;
         MyStack(int cap){
             this.cap = cap;
             this.arr = new int[cap];
             this.top =-1;
         }

        public void push(int data) {
            if (top == cap - 1) {
                System.out.println("Stack Overflow");
                return;
            }
            top++;
            arr[top] = data;
        }

        public int pop() {
            if (top == -1) {
                System.out.println("Stack Underflow");
                return -1;
            }
            int res = arr[top];
            top--;
            return res;
        }

        public int peek() {
            if (top == -1) {
                System.out.println("Stack is empty");
                return -1;
            }
            return arr[top];
        }

        public int size() {
            return top + 1;
        }
    }

    public static void main(String[] args){
        MyStack s = new MyStack(5);
        s.push(10);
        s.push(20);
        s.pop();
        System.out.println(s.peek());
    }
}
