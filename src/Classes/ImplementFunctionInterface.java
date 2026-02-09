package Classes;

import interfaces.TestFunctionalInterface;

public class ImplementFunctionInterface implements TestFunctionalInterface {

    @Override
    public void test() {
        System.out.println("Overridden test method");
    }

    public static void main(String[] args) {
        TestFunctionalInterface obj = () -> {
            System.out.println("Inside test method");
        };

        obj.test();
    }
}