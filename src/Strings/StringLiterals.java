package Strings;

public class StringLiterals {

    public static void main(String[] args) {

        String s1 = "hello";
        String s2 = "hello";

        s1.concat("world");

        System.out.println(s1 == s2);// Output: true
        System.out.println(s1.equals(s2)); // Output: true


        String s3 = new String("hello");

        System.out.println(s1 == s3); // Output: false
        System.out.println(s1.equals(s3)); // Output: true
    }
}
