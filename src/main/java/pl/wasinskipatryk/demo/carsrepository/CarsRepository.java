package pl.wasinskipatryk.demo.carsrepository;

import pl.wasinskipatryk.demo.car.Car;

import java.util.List;

public  interface CarsRepository {

    List<Car> findAll();

    Car findByModelName(String modelName);

    boolean add(Car car);

    Car update(Car oldCar, Car newCar);

    boolean delete(Car car);

}

