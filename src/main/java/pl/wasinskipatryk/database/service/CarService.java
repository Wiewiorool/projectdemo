package pl.wasinskipatryk.database.service;

import jakarta.transaction.Transactional;
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
import java.util.Optional;

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
    @Transactional
    public long addNewCar(
            String modelName, String colour, int productionYear,
            int horsePower, int nrOfDoors, TypeOfCar typeOfCar, BigDecimal buyPrice) {

        TypeOfCarEntity typeOfCarEntity = typeOfCarRepository.findByTypeOfCar(String.valueOf(typeOfCar));

        //Jesli typeOfCar bylo to trzeba poszukac w cardetailsach
        //SELECT *  FROM car_details
        //where model_name = 'Audi' and color ='Black' and horse_power = 250 and number_of_doors =
        // 4 and production_year = 2,204 and type_of_car_id = typeOfCarEntitny.getId()!=0

        if (typeOfCarEntity == null) {
            log.info("Type of car does not exist");
            TypeOfCarEntity toce = TypeOfCarEntity.builder()
                .value(typeOfCar.name())
                .build();
            CarDetailsEntity cde = CarDetailsEntity.builder()
                .modelName(modelName)
                .color(colour)
                .productionYear(productionYear)
                .horsePower(horsePower)
                .numberOfDoors(nrOfDoors)
                .typeOfCar(toce)
                .build();
            CarEntity newSavedCar = saveNewCar(cde, buyPrice);
            return newSavedCar.getCarId();
        } else {
            log.info("Type of car does exist");
            CarDetailsEntity carDetailsEntity = carDetailsRepository.findSpecificCarDetails(modelName, colour, horsePower,
                    nrOfDoors, productionYear, typeOfCarEntity.getTypeOfCarId());

            if (carDetailsEntity == null) {
                log.info("Car details don't exist");
                CarDetailsEntity cde = CarDetailsEntity.builder()
                    .modelName(modelName)
                    .color(colour)
                    .productionYear(productionYear)
                    .horsePower(horsePower)
                    .numberOfDoors(nrOfDoors)
                    .typeOfCar(typeOfCarEntity)
                    .build();
                CarEntity newSavedCar = saveNewCar(cde, buyPrice);
                return newSavedCar.getCarId();
            } else {
                log.info("Matching car details found.");
                CarEntity newCar = saveNewCar(carDetailsEntity, buyPrice);
                return newCar.getCarId();
            }
        }
    }

    private CarEntity saveNewCar(CarDetailsEntity carDetailsEntity, BigDecimal buyPrice) {
        CarEntity newSave = carRepository.save(CarEntity.builder()
                .carDetails(carDetailsEntity)
                .buyCarPrice(buyPrice)
                .build());
        return newSave;
    }
}
