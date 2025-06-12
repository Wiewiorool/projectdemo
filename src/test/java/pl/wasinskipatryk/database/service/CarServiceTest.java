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
    }
}