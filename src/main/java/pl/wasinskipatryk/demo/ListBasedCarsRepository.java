package pl.wasinskipatryk.demo;

import java.util.ArrayList;
import java.util.List;

public class ListBasedCarsRepository implements CarsRepository {
    private final List<Car> cars = new ArrayList<>();

    @Override
    public List<Car> findAll() {
        return cars;
    }

    @Override
    public Car findByModelName(String modelName) {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            if (car.getCarDetails().getModelName().equals(modelName)) {
                return car;
            }
        }
        return null;
    }

    @Override
    public boolean add(Car carToAdd) {
        cars.add(carToAdd);
        return true;
    }

    @Override
    public void update(Car oldCar, Car newCar) {
        delete(oldCar);
        add(newCar);
    }

    @Override
    public void delete(Car carToBeDeleted) {
        cars.remove(carToBeDeleted);
    }

}
