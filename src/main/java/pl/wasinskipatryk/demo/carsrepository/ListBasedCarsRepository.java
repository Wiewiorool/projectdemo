package pl.wasinskipatryk.demo.carsrepository;

import pl.wasinskipatryk.demo.car.Car;

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
        return cars.add(carToAdd);
    }

    @Override
    public void update(Car oldCar, Car newCar) {
        int indexOfOldCar = cars.indexOf(oldCar);

        //int	indexOf(String str)
        //Zwraca indeks pozycji pierwszego wystąpienia w danym napisie
        // napisu podanego jako argument str; jeżeli str nie występuje w tym napisie - zwraca -1

        if (indexOfOldCar != -1) {
            cars.set(indexOfOldCar, newCar);
        }
    }

    @Override
    public void delete(Car carToBeDeleted) {
        cars.remove(carToBeDeleted);
    }

}
