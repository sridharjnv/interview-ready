package DSA.Stack;

import java.util.Stack;

public class StockSpan {
    public static void main(String[] args){
        int[] arr = {60,10,20,15,35,50};
        printSpan(arr);
    }
    static void printSpan(int[] arr){
        int n = arr.length;
        Stack<Integer> s = new Stack<>();
        s.push(0);
        System.out.println(1);
        for(int i=1;i<n;i++){
            while(!s.isEmpty() && arr[s.peek()]<=arr[i]){
                s.pop();
            }
            int span = s.isEmpty() ? i+1 : i-s.peek();
            System.out.println(span);
            s.push(i);
        }
    }
}
