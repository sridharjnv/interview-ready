package collections;

import Classes.Car;

import java.util.Arrays;

public class ComparableVsComparator {

    int a =5;

    public static void main(String[] args) {

        //1. primitive sorting
        Integer[] arr = {10, 7, 8, 9, 1, 5};
        int a = arr[0];
        Arrays.sort(arr); // it will sort in ascending order but what if we need in descending order?
        Arrays.sort(arr, (Integer val1, Integer val2) -> val2 - val1); // this will sort in descending order


        Car[] carArray = new Car[3];
        carArray[0] = new Car("SUV", "petrol");
        carArray[1] = new Car("Sedan", "diesel");
        carArray[2] = new Car("Hatchback", "CNG");

        Arrays.sort(carArray, (Car obj1, Car obj2) -> obj2.getCarType().compareTo(obj1.getCarType()));//
    }

}
