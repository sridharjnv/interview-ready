package Classes;

import interfaces.Bird;

public class Sparrow implements Bird {

    @Override
    public void fly() {
        System.out.println("Sparrow is flying");
    }

    @Override
    public void sing() {
        System.out.println("Sparrow is singing");
    }

    public static void main(String[] args) {
        Sparrow sparrow = new Sparrow();
        sparrow.fly();
        sparrow.sing();

        sparrow.getMinimumFlyHeight(); // Calls the default method
    }

}
