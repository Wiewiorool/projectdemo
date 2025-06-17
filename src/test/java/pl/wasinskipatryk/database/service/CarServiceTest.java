package pl.wasinskipatryk.database.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pl.wasinskipatryk.database.enitities.CarEntity;
import pl.wasinskipatryk.database.repositories.CarRepository;
import pl.wasinskipatryk.demo.car.TypeOfCar;

import java.math.BigDecimal;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CarServiceTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Test
    void addNewCar() {
        // given

        // when
        long newCarId = carService.addNewCar("Golf", "gray", 2025, 140, 5,
                TypeOfCar.HATCHBACK, BigDecimal.valueOf(10_000)
        );

        // then
        Assertions.assertNotNull(carRepository.findById(newCarId));

    }

    @Test
    void shouldGetAllCars() {
        //given
        carService.addNewCar("Audi", "gray", 2025, 140, 5,
                TypeOfCar.HATCHBACK, BigDecimal.valueOf(10_000));

        carService.addNewCar("BMW", "blue", 2025, 140, 2,
                TypeOfCar.HATCHBACK, BigDecimal.valueOf(15_000));

        //when
        List<CarEntity> actual = carService.findAllCars();

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());


        assertCar(actual.get(0), "Audi", "gray", 2025, 140, 5, "HATCHBACK", 10_000);
        assertCar(actual.get(1), "BMW", "blue", 2025, 140, 2, "HATCHBACK", 15_000);
    }

    private static void assertCar(CarEntity carEntity, String modelName, String color, int year,
                                  int horsePower, int doors, String typeOfCar, int sellPrice) {
        Assertions.assertEquals(modelName, carEntity.getCarDetails().getModelName());
        Assertions.assertEquals(color, carEntity.getCarDetails().getColor());
        Assertions.assertEquals(year, carEntity.getCarDetails().getProductionYear());
        Assertions.assertEquals(horsePower, carEntity.getCarDetails().getHorsePower());
        Assertions.assertEquals(doors, carEntity.getCarDetails().getNumberOfDoors());
        Assertions.assertEquals(typeOfCar, carEntity.getCarDetails().getTypeOfCar().getValue());
        Assertions.assertEquals(BigDecimal.valueOf(sellPrice).stripTrailingZeros(), carEntity.getBuyCarPrice().stripTrailingZeros());
    }
}