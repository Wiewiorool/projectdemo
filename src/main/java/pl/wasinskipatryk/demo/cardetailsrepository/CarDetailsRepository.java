package pl.wasinskipatryk.demo.cardetailsrepository;

import pl.wasinskipatryk.demo.car.CarDetails;

import java.util.List;

public interface CarDetailsRepository {

    List<CarDetails> findAllCarDetails();

    int addCarDetails(CarDetails carDetails);

    CarDetails findCarDetails(CarDetails carDetails);




}
