package Classes;

import java.util.Comparator;

public class Car implements Comparable<Car>, Comparator<Car> {
    String carName;
    String carType;

    public Car(String carName, String carType) {
        this.carName = carName;
        this.carType = carType;
    }

    public String getCarName() {
        return carName;
    }

    public String getCarType() {
        return carType;
    }

    @Override
    public int compareTo(Car car) {
        return this.carName.compareTo(car.getCarName());
    }

    @Override
    public int compare(Car car1, Car car2) {
        return car2.carType.compareTo(car1.carType);
    }

}
