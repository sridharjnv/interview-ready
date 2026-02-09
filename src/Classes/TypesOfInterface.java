package Classes;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TypesOfInterface {

    public static void main(String[] args) {

        // Consumer Functional Interface
        Consumer<Integer> loggingObj = (Integer val) -> {
            if (val > 10) {
                System.out.println("Value: " + val);
            }
        };
        loggingObj.accept(15);


        // Supplier Functional Interface
        Supplier<String> isEvenNumber = () -> "this is even Number";
        System.out.println(isEvenNumber.get());

        //Function Functional Interface
        Function<Integer, String> integerToString =
                (Integer val) -> {
            String output = val.toString();
            return output;
        };
        integerToString.apply(10);


        //Predicate Functional Interface
        Predicate<Integer> isEven = (Integer val) -> {
            if(val%2==0){
                return true;
            }
            return false;
        };
        isEven.test(10);

    }
}
