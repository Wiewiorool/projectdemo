package pl.wasinskipatryk.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.wasinskipatryk.database.repositories.CarDetailsRepository;
import pl.wasinskipatryk.database.repositories.CarRepository;
import pl.wasinskipatryk.database.repositories.TypeOfCarRepository;

public class CarService {
    private final TypeOfCarRepository typeOfCarRepository;
    private final CarDetailsRepository carDetailsRepository;
    private final CarRepository carRepository;

    public CarService(@Autowired TypeOfCarRepository typeOfCarRepository,
                      @Autowired CarDetailsRepository carDetailsRepository,
                      @Autowired CarRepository carRepository) {
        this.carDetailsRepository = carDetailsRepository;
        this.carRepository = carRepository;
        this.typeOfCarRepository = typeOfCarRepository;
    }

    public long addNewCar(
            String modelName, String colour, int productionYear,
            int horsePower, int nrOfDoors, TypeOfCar, BigDecimal buyPrice) {



    }

}
