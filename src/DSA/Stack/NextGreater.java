package DSA.Stack;
import java.util.Stack;

public class NextGreater {
    public static void main(String[] args){
        int[] arr = {5,15,10,8,6,12,9,18};
        int[] ls = nextGreatest(arr);
        for(int num:ls) System.out.println(num);
    }
    static int[] nextGreatest(int[] arr){
        int n = arr.length;
        int[] res = new int[n];
        Stack<Integer> s = new Stack<>();

        for(int i = n - 1; i >= 0; i--){
            while(!s.isEmpty() && s.peek() <= arr[i]){
                s.pop();
            }
            res[i] = s.isEmpty() ? -1 : s.peek();
            s.push(arr[i]);
        }
        return res;
    }
}
