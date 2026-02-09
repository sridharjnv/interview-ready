package Classes;

import java.util.Arrays;

public class ReflectionMain {

    public static void main(String[] args) {
        Class<Reflection> reflection = Reflection.class;

        System.out.println(reflection.getName());
        System.out.println(reflection.getModifiers());
        System.out.println(Arrays.toString(reflection.getDeclaredMethods()));
        System.out.println(Arrays.toString(reflection.getMethods()));


    }
}
