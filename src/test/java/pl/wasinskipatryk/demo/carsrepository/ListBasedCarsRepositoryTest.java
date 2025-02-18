package pl.wasinskipatryk.demo.carsrepository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.wasinskipatryk.demo.car.Car;

class ListBasedCarsRepositoryTest {
  private CarsRepository listBasedCarsRepository = new ListBasedCarsRepository();

  @Test
  public void shouldAddCar(){
    //given
    Car carToBeAdded = Car.builder()
        .id(100)
        .build();

    //when
    boolean actual = listBasedCarsRepository.add(carToBeAdded);

    //then
    Assertions.assertTrue(actual);
  }

  @Test
  public void shouldReturnAllCars() {

  // HW
  }

  @Test
  public void shouldReturnFindByModelName() {

    // HW
  }
  //itd
}