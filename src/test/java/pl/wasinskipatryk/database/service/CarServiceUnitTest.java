package pl.wasinskipatryk.database.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static pl.wasinskipatryk.demo.car.TypeOfCar.HATCHBACK;

import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.wasinskipatryk.database.enitities.CarDetailsEntity;
import pl.wasinskipatryk.database.enitities.CarEntity;
import pl.wasinskipatryk.database.enitities.TypeOfCarEntity;
import pl.wasinskipatryk.database.repositories.CarDetailsRepository;
import pl.wasinskipatryk.database.repositories.CarRepository;
import pl.wasinskipatryk.database.repositories.TypeOfCarRepository;
import pl.wasinskipatryk.demo.car.TypeOfCar;

public class CarServiceUnitTest {

  private CarService carService;

  @Mock
  private TypeOfCarRepository typeOfCarRepository;
  @Mock
  private CarDetailsRepository carDetailsRepository;
  @Mock
  private CarRepository carRepository;

  @Mock
  private TypeOfCarEntity typeOfCarEntity;
  @Mock
  private CarDetailsEntity carDetailsEntity;
  @Mock
  private CarEntity carEntity;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    carService = new CarService(typeOfCarRepository, carDetailsRepository, carRepository);
  }

  @AfterEach
  public void tearDown() {

  }

  @Test
  public void shouldAddNewCarWhenTypeOfCarExistsAndCarDetailsExists() {
    //given
    String modelName = "Golf";
    String colour = "gray";
    int productionYear = 2025;
    int horsePower = 140;
    int nrOfDoors = 5;
    BigDecimal buyPrice = BigDecimal.valueOf(10_000);
    TypeOfCar typeOfCar = HATCHBACK;
    long typeOfCarId = 1;
    long expectedNewCarId = 10L;

    when(typeOfCarRepository.findByTypeOfCar(eq(String.valueOf(typeOfCar)))).thenReturn(typeOfCarEntity);
    when(typeOfCarEntity.getTypeOfCarId()).thenReturn(typeOfCarId);
    when(carDetailsRepository.findSpecificCarDetails(
        eq(modelName), eq(colour), eq(horsePower), eq(nrOfDoors), eq(productionYear), eq(typeOfCarId)))
        .thenReturn(carDetailsEntity);
    when(carRepository.save(any())).thenReturn(carEntity);
    when(carEntity.getCarId()).thenReturn(expectedNewCarId);

    //when
    long newCarId = carService.addNewCar(modelName, colour, productionYear, horsePower, nrOfDoors,
                                         typeOfCar, buyPrice
    );

    //then
    Assertions.assertEquals(expectedNewCarId, newCarId);
  }

  @Test
  public void shouldAddNewCarWhenTypeOfCarExistsAndCarDetailsNull() {
    //given
    String modelName = "Golf";
    String colour = "gray";
    int productionYear = 2025;
    int horsePower = 140;
    int nrOfDoors = 5;
    BigDecimal buyPrice = BigDecimal.valueOf(10_000);
    TypeOfCar typeOfCar = HATCHBACK;
    long typeOfCarId = 1;
    long expectedNewCarId = 10L;

    when(typeOfCarRepository.findByTypeOfCar(eq(String.valueOf(typeOfCar)))).thenReturn(typeOfCarEntity);
    when(typeOfCarEntity.getTypeOfCarId()).thenReturn(typeOfCarId);
    when(carDetailsRepository.findSpecificCarDetails(
        eq(modelName), eq(colour), eq(horsePower), eq(nrOfDoors), eq(productionYear), eq(typeOfCarId)))
        .thenReturn(null);
    when(carRepository.save(any())).thenReturn(carEntity);
    when(carEntity.getCarId()).thenReturn(expectedNewCarId);

    //when
    long newCarId = carService.addNewCar(modelName, colour, productionYear, horsePower, nrOfDoors,
                                         typeOfCar, buyPrice
    );

    //then
    Assertions.assertEquals(expectedNewCarId, newCarId);
  }

  @Test
  public void shouldAddNewCarWhenTypeOfCarNull() {
    //given
    String modelName = "Golf";
    String colour = "gray";
    int productionYear = 2025;
    int horsePower = 140;
    int nrOfDoors = 5;
    BigDecimal buyPrice = BigDecimal.valueOf(10_000);
    TypeOfCar typeOfCar = HATCHBACK;
    long expectedNewCarId = 10L;

    when(typeOfCarRepository.findByTypeOfCar(eq(String.valueOf(typeOfCar)))).thenReturn(null);

    when(carRepository.save(any())).thenReturn(carEntity);
    when(carEntity.getCarId()).thenReturn(expectedNewCarId);

    //when
    long newCarId = carService.addNewCar(modelName, colour, productionYear, horsePower, nrOfDoors,
                                         typeOfCar, buyPrice
    );

    //then
    Assertions.assertEquals(expectedNewCarId, newCarId);
    verifyNoInteractions(carDetailsRepository);
    verify(carDetailsRepository, times(0)).findSpecificCarDetails(
        anyString(), anyString(), anyInt(), anyInt(), anyInt(), anyLong()
    );
  }
}
