package pl.wasinskipatryk.demo.carsrepository;

import pl.wasinskipatryk.demo.car.Car;

import java.util.List;

public  interface CarsRepository {

    List<Car> findAll();

    Car findByModelName(String modelName);

    boolean add(Car car);

    void update(Car oldCar, Car newCar);

    void delete(Car car);

}

