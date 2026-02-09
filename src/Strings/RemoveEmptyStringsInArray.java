package Strings;

import java.util.Arrays;
import java.util.Objects;

public class RemoveEmptyStringsInArray {

    public static void main(String[] args) {
        String[] arr = {"","python","java","c"," ","javascript",null};

        String[] names = Arrays.stream(arr)
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);
        System.out.println(Arrays.toString(names));
    }

    //to filter empty strings, use isBlank()
    //to filter null values, use Objects::nonNull
    //to filter strings with length 0, use isEmpty()
}
