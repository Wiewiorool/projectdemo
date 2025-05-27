package pl.wasinskipatryk.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wasinskipatryk.database.enitities.CarDetailsEntity;
import pl.wasinskipatryk.database.enitities.CarEntity;
import pl.wasinskipatryk.database.enitities.TypeOfCarEntity;
import pl.wasinskipatryk.database.repositories.CarDetailsRepository;
import pl.wasinskipatryk.database.repositories.CarRepository;
import pl.wasinskipatryk.database.repositories.TypeOfCarRepository;
import pl.wasinskipatryk.demo.car.TypeOfCar;

import java.math.BigDecimal;

@Service
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
            int horsePower, int nrOfDoors, TypeOfCar typeOfCar, BigDecimal buyPrice) {
        TypeOfCarEntity typeOfCarEntity = typeOfCarRepository.findByTypeOfCar(String.valueOf(typeOfCar));

        if(typeOfCarEntity == null){
            typeOfCarEntity = TypeOfCarEntity.builder()
                    .value(String.valueOf(typeOfCar))
                    .build();
        }

        CarDetailsEntity carDetails = CarDetailsEntity.builder()
                .modelName(modelName)
                .color(colour)
                .productionYear(productionYear)
                .horsePower(horsePower)
                .numberOfDoors(nrOfDoors)
                .typeOfCar(typeOfCarEntity)
                .build();
        CarEntity carEntity = CarEntity.builder()
                .buyCarPrice(buyPrice)
                .carDetails(carDetails)
                .build();
        CarEntity newCarEntity = carRepository.save(carEntity);
        return newCarEntity.getCarId();

    }

}
