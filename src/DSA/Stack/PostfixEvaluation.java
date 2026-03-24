package DSA.Stack;
import java.util.Stack;

public class PostfixEvaluation {
    public static void main(String[] args) {
        String exp = "23*54*+9-";
        System.out.println(evaluatePostfix(exp));
    }
    static int evaluatePostfix(String exp) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);
            // If operand (digit)
            if (Character.isDigit(ch)) {
                stack.push(ch - '0'); // convert char to int
            }
            else {
                int b = stack.pop();
                int a = stack.pop();
                int result = 0;

                switch (ch) {
                    case '+': result = a + b; break;
                    case '-': result = a - b; break;
                    case '*': result = a * b; break;
                    case '/': result = a / b; break;
                }
                stack.push(result);
            }
        }
        return stack.pop();
    }
}
