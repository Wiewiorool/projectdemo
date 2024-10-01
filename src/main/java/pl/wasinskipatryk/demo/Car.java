package pl.wasinskipatryk.demo;

public class Car {

    private CarDetails carDetails;

    private CarPrice carPrice;

    public Car(CarPrice carPrice, CarDetails carDetails) {
        this.carDetails = carDetails;
        this.carPrice = carPrice;
    }
}
