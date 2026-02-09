package Classes;

import interfaces.LandAnimal;
import interfaces.WaterAnimal;

public class Animal implements LandAnimal, WaterAnimal {

    @Override
    public boolean canBreathe(){
        return true;
    }

    public static void main(String[] args) {
        Animal obj = new Animal();
        obj.canBreathe(); // Output: true
    }
}
