package DSA.Stack;

import java.util.Stack;

public class MinStackWithSingleStack {
    Stack<Integer> s = new Stack<>();
    int min;

    public void push(int x){
        if(s.isEmpty()){
            s.push(x);
            min = x;
        }
        else if(x <= min){
            s.push(2*x-min);
            min=x;
        }
        else s.push(x);
    }

    public int pop(){
        int t = s.pop();
        if(t <= min){
            int res = min;
            min = 2*min -t;
            return res;
        }
        else{
            return t;
        }
    }

    public int getMin(){
        if(!s.isEmpty()) return min;
        else return -1;
    }

    public int peek(){
        if(!s.isEmpty()) return s.peek();
        else return -1;
    }
    public static void main(String[] args){
        MinStackWithSingleStack s = new MinStackWithSingleStack();
        s.push(10);
        s.push(20);
        s.push(30);
        s.pop();
    }

}
