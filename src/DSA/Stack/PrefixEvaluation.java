package DSA.Stack;

import java.util.Stack;

public class PrefixEvaluation {

    public static void main(String[] args) {
        String exp = "-+7*45+20";
        System.out.println(evaluatePrefix(exp));
    }

    static int evaluatePrefix(String exp) {
        Stack<Integer> stack = new Stack<>();

        // Traverse from right to left
        for (int i = exp.length() - 1; i >= 0; i--) {
            char ch = exp.charAt(i);

            // If operand
            if (Character.isDigit(ch)) {
                stack.push(ch - '0');
            }
            // If operator
            else {
                int a = stack.pop();
                int b = stack.pop();

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
