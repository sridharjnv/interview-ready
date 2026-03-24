package DSA.Stack;

import java.util.Stack;

public class LongestAreaHistogram {
    public static void main(String[] args){
        int[] arr  ={60,20,50,40,10,50,60};
        System.out.println(getMaxArea(arr));
    }
    static int getMaxArea(int[] arr){
        int n = arr.length;
        Stack<Integer> s = new Stack<>();
        int res =0;
        for(int i=0;i<n;i++){
            while(!s.isEmpty() && arr[s.peek()] >=arr[i]){
                int top = s.pop();
                int curr = arr[top] * (s.isEmpty() ? i : (i- s.peek() -1));
                res = Math.max(curr,res);
            }
            s.push(i);
        }
        while(!s.isEmpty()){
            int top = s.pop();
            int curr = arr[top] * (s.isEmpty() ? n : (n-s.peek()-1));
            res = Math.max(curr,res);
        }
        return res;
    }
}
