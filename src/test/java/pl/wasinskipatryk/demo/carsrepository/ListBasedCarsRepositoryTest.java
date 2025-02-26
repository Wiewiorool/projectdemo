package pl.wasinskipatryk.demo.carsrepository;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarDetails;


class ListBasedCarsRepositoryTest {
    private CarsRepository listBasedCarsRepository = new ListBasedCarsRepository();


    @Test
    public void shouldAddCar() {
        //given
        Car carToBeAdded = Car.builder()
                .carDetails(
                        CarDetails.builder()
                                .modelName("Audi")
                                .build()
                )
                .id(100)
                .build();

        //when
        boolean actual = listBasedCarsRepository.add(carToBeAdded);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    public void shouldReturnAllCars() {
        //given
        // HW
    }

    @Test
    public void shouldReturnFindByModelName() {
        //given
        String model = "Audi";

        //when

        Car result = listBasedCarsRepository.findByModelName(model);
        //then

        Assertions.assertNull(result);
        Assertions.assertEquals(model,result.getCarDetails().getModelName());


        // HW
    }
    //itd
}