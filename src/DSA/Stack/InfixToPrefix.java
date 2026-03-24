package DSA.Stack;

import java.util.Stack;

public class InfixToPrefix{

    public static void main(String[] args) {
        String exp = "(A+B)*C";
        System.out.println(infixToPrefix(exp));
    }

    static int precedence(char ch) {
        switch (ch) {
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
            case '^': return 3;
        }
        return -1;
    }

    static String infixToPrefix(String exp) {

        // 1. Reverse expression
        StringBuilder input = new StringBuilder(exp);
        input.reverse();

        // 2. Swap brackets
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                input.setCharAt(i, ')');
            } else if (input.charAt(i) == ')') {
                input.setCharAt(i, '(');
            }
        }

        // 3. Convert to postfix
        String postfix = infixToPostfix(input.toString());

        // 4. Reverse postfix → prefix
        return new StringBuilder(postfix).reverse().toString();
    }

    static String infixToPostfix(String exp) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);

            // Operand
            if (Character.isLetterOrDigit(ch)) {
                result.append(ch);
            }
            // Opening bracket
            else if (ch == '(') {
                stack.push(ch);
            }
            // Closing bracket
            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                stack.pop();
            }
            // Operator
            else {
                while (!stack.isEmpty() &&
                        precedence(stack.peek()) >= precedence(ch)) {
                    result.append(stack.pop());
                }
                stack.push(ch);
            }
        }

        // Pop remaining
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }
}