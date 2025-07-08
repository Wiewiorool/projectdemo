package pl.wasinskipatryk.database.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.wasinskipatryk.database.enitities.ClientEntity;
import pl.wasinskipatryk.database.repositories.CarRepository;
import pl.wasinskipatryk.database.repositories.ClientRepository;

class ClientServiceTest {
  private ClientService clientService;
  @Mock
  private CarRepository carRepository;
  @Mock
  private ClientRepository clientRepository;
  @Mock
  private ClientEntity client1;
  @Mock
  private ClientEntity client2;
  private long carId = 1;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    clientService = new ClientService(clientRepository, carRepository);
  }

  @Test
  public void shouldThrowExceptionWhenMoreThan1ClientForCarId(){
    //given
    when(clientRepository.findByCarId(eq(carId))).thenReturn(List.of(client1, client2));

    //when
    Assertions.assertThrows(IllegalStateException.class, () -> clientService.findClientForCarId(carId));

    //then
    verify(clientRepository).findByCarId(eq(carId));
    verifyNoMoreInteractions(clientRepository);
  }

  @Test
  public void shouldThrowExceptionWhenNoClientForCarId(){
    //given
    when(clientRepository.findByCarId(eq(carId))).thenReturn(List.of());

    //when
    Assertions.assertThrows(IllegalStateException.class, () -> clientService.findClientForCarId(carId));

    //then
    verify(clientRepository).findByCarId(eq(carId));
    verifyNoMoreInteractions(clientRepository);
  }

  @Test
  public void shouldReturnClientForCarId(){
    //given
    when(clientRepository.findByCarId(eq(carId))).thenReturn(List.of(client1));

    //when
    var actual =  clientService.findClientForCarId(carId);

    //then
    Assertions.assertEquals(client1, actual);
    verify(clientRepository).findByCarId(eq(carId));
    verifyNoMoreInteractions(clientRepository);
  }
}