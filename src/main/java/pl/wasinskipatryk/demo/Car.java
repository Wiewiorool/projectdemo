package pl.wasinskipatryk.demo;

import java.util.Objects;

public class Car {

    private CarDetails carDetails;

    private CarPrice carPrice;

    public Car(CarPrice carPrice, CarDetails carDetails) {
        this.carDetails = carDetails;
        this.carPrice = carPrice;
    }

    public CarDetails getCarDetails() {
        return carDetails;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carDetails=" + carDetails +
                ", carPrice=" + carPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(carDetails, car.carDetails) && Objects.equals(carPrice, car.carPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carDetails, carPrice);
    }
}
