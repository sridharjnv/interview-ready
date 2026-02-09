package interfaces;

@FunctionalInterface
public interface TestFunctionalInterface {

    public void test();

    public default String getName() {
        return "Hi";
    };

    public static void staticMethod() {};

    private void privateStaticMethod() {};

    private static void privateMethod() {};
}
