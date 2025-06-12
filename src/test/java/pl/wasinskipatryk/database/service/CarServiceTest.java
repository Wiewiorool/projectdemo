package pl.wasinskipatryk.database.service;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.wasinskipatryk.database.repositories.CarRepository;
import pl.wasinskipatryk.demo.car.TypeOfCar;

@ActiveProfiles("test")
@SpringBootTest
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
}