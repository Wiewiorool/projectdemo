package pl.wasinskipatryk.database.service;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        CarDetailsEntity carDetailsEntity;

        //Jesli typeOfCar bylo to trzeba poszukac w cardetailsach
        //SELECT *  FROM car_details
        //where model_name = 'Audi' and color ='Black' and horse_power = 250 and number_of_doors =
        // 4 and production_year = 2,204 and type_of_car_id = typeOfCarEntitny.getId()!=0

        if (typeOfCarEntity == null) {
            log.info("Type of car does not exist");
            typeOfCarEntity = TypeOfCarEntity.builder()
                    .value(String.valueOf(typeOfCar))
                    .build();
            typeOfCarEntity = typeOfCarRepository.save(typeOfCarEntity);

            carDetailsEntity = CarDetailsEntity.builder()
                    .modelName(modelName)
                    .color(colour)
                    .productionYear(productionYear)
                    .horsePower(horsePower)
                    .numberOfDoors(nrOfDoors)
                    .typeOfCar(typeOfCarEntity)
                    .build();
            carDetailsEntity = carDetailsRepository.save(carDetailsEntity);
        } else {
            if (typeOfCarEntity.getTypeOfCarId() != 0) {
                log.info("Type of car does exist");
                carDetailsEntity = carDetailsRepository.findSpecificCarDetails(modelName, colour, horsePower,
                        nrOfDoors, productionYear, typeOfCar);

                if (carDetailsEntity == null) {
                    log.info("Car details don't exist");
                    carDetailsEntity = CarDetailsEntity.builder()
                            .modelName(modelName)
                            .color(colour)
                            .productionYear(productionYear)
                            .horsePower(horsePower)
                            .numberOfDoors(nrOfDoors)
                            .typeOfCar(typeOfCar)
                            .build();
                    carDetailsEntity = carDetailsRepository.save(carDetailsEntity);
                } else {
                    log.info("Matching car details found.");
                }
            }
        }
        CarEntity carEntity = CarEntity.builder()
                .carDetails(carDetailsEntity)
                .buyCarPrice(buyPrice)
                .build();
        CarEntity newCarEntity = carRepository.save(carEntity);
        return newCarEntity.getCarId();
    }
}
