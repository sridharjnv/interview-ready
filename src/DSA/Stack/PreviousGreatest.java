package DSA.Stack;

import java.util.Stack;

public class PreviousGreatest {
    public static void main(String[] args){
        int[] arr = {20,30,10,5,15};
        previousGreater(arr);
    }
    static void previousGreater(int[] arr){
        int n = arr.length;
        Stack<Integer> s = new Stack<>();
        s.push(arr[0]);
        System.out.println(-1);
        for(int i=1;i<n;i++){
            while(!s.isEmpty() && s.peek()<=arr[i]){
                s.pop();
            }
            int pg = s.isEmpty() ? -1 : s.peek();
            System.out.println(pg);
            s.push(arr[i]);
        }
    }
}
