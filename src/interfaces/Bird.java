package interfaces;

public interface Bird {

    int MAX_COUNT = 2;// here public static final is added implicitly
    public static final int number = 1; // both are same

    public void fly(); // abstract method and public abstract keyword is added implicitly

    public void sing(); // abstract method

    default void getMinimumFlyHeight() {
        System.out.println("Minimum fly height: 10 feet");
    } // default method

    public default void publicDefaultMethod() {
        staticMethod(); // call static method
        privateMethod(); // call private method
        privateStaticMethod(); // call private static method
    }

    static void staticMethod() {
        System.out.println("This is a static method in Bird interface");
        privateStaticMethod(); // from inside static method we can call private static method only

    } // static method

    private void privateMethod() {
        System.out.println("This is a private method in Bird interface");
    } // private method

    private static void privateStaticMethod() {
        System.out.println("This is a private static method in Bird interface");
    } // private static method

}
