package DSA.Stack;

import java.util.Stack;

public class ValidParanthesis {
    public static void main(String[] args){
        String str = "((())))";
        System.out.println(isValid(str));
    }
    public static boolean isValid(String str){
        Stack<Character> s = new Stack<>();
        for(Character ch : str.toCharArray()){
            if(ch == '{' || ch == '(' || ch == '['){
                s.push(ch);
            }
            else{
                if(s.isEmpty()) return false;
                Character top = s.pop();
                if((ch == '}' && top != '{')
                || (ch == ']' && top != '[')
            || (ch == ')' && top != '(')) return false;
            }

        }
        return s.isEmpty();
    }
}
