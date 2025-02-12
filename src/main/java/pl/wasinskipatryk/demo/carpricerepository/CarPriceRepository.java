package pl.wasinskipatryk.demo.carpricerepository;

import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarPrice;

import java.util.List;

public interface CarPriceRepository {

    int addCarPrice(CarPrice carToBeAdded);

    List<CarPrice> findAllCarPrice();

    CarPrice findCarPrice(CarPrice carPrice);
}
