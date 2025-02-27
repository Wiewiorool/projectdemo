package pl.wasinskipatryk.demo.carsrepository;

import java.util.List;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarDetails;


class ListBasedCarsRepositoryTest {
    private CarsRepository listBasedCarsRepository = new ListBasedCarsRepository();


    @Test
    public void shouldFindNoCars() {
        // given - potrzebne dane

        // when - tu jest metoda, którą testujemy
        List<Car> actual = listBasedCarsRepository.findAll();

        // then - będzie sprawdzanie czy zwróciło się to czego się spodziewamy
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    public void shouldFindAllCars() {
        // given - potrzebne dane
        // tworzymy 2 auta
        Car car1 = Car.builder()
                .id(1)
                .build();
        Car car2 = Car.builder()
                .id(2)
                .build();
        // dodajemy te 2 auta
        listBasedCarsRepository.add(car1);
        listBasedCarsRepository.add(car2);

        // when - tu jest metoda, którą testujemy
        List<Car> actual = listBasedCarsRepository.findAll();

        // then - będzie sprawdzanie czy zwróciło się to czego się spodziewamy
        // spodziewamy sie ze te 2 auta sie zwrócą
        Assertions.assertFalse(actual.isEmpty());
        Assertions.assertTrue(actual.size() == 2);
        Assertions.assertEquals(car1, actual.get(0));
        Assertions.assertEquals(car2, actual.get(1));
    }

    @Test
    public void shouldAddCar() {
        //given
        Car carToBeAdded = Car.builder()
                .id(100)
                .carDetails(
                        CarDetails.builder()
                                .modelName("Audi")
                                .build()
                )
                .build();

        //when
        boolean actual = listBasedCarsRepository.add(carToBeAdded);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    public void shouldReturnFindByModelName() {
        //given
        String model = "Audi";
        Car car1 = Car.builder()
                .id(1)
                .carDetails(CarDetails.builder().modelName(model).build())
                .build();
        // dodajemy audi do listy
        listBasedCarsRepository.add(car1);

        //when
        Car result = listBasedCarsRepository.findByModelName(model);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(model, result.getCarDetails().getModelName());
    }

    @Test
    public void shouldDeleteCar() {
        //given
        Car car = Car.builder()
                .id(1)
                .build();
        listBasedCarsRepository.add(car);

        //when
        boolean deleted = listBasedCarsRepository.delete(car);

        //then
        Assertions.assertTrue(deleted);

    }

    @Test
    public void shouldNotDeleteCarIfItDidNotExist() {
        //given
        Car car = Car.builder()
                .id(1)
                .build();

        //when
        boolean deleted = listBasedCarsRepository.delete(car);

        //then
        Assertions.assertFalse(deleted);

    }

    @Test
    public void shouldUpdateCar() {
        //given
        Car oldCar = Car.builder()
                .id(1)
                .build();
        Car newCar = Car.builder()
                .id(2)
                .build();
        listBasedCarsRepository.add(oldCar);

        //when
        Car result = listBasedCarsRepository.update(oldCar, newCar);

        //then
        Assertions.assertEquals(newCar,result);

    }

}