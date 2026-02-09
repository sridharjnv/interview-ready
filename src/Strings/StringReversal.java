package Strings;

import java.util.Scanner;

public class StringReversal {

    public static String reverserString(String input) {

        if(input == null || input.trim().isEmpty()) {
            return input;
        }

        String[] word = input.split("\\s");
        StringBuilder reversed = new StringBuilder();

        for(int i=word.length-1;i>=0;i--) {
            reversed.append(word[i]);
            // if you want to reverse each word individually use below line instead of above one
            //reversed.append(new StringBuilder(word[i]).reverse()).append(" ");
            if(i>0) {
                reversed.append(" ");
            }
        }
        return reversed.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a string:");
        String input = sc.nextLine();
        sc.close();

        String reverse = reverserString(input);
        System.out.print("Reversed String: "+ reverse);
    }
}

// Time Complexity: O(n), where n is the length of the input string.