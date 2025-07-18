package pl.wasinskipatryk.database.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.wasinskipatryk.database.enitities.ClientEntity;
import pl.wasinskipatryk.database.repositories.CarRepository;
import pl.wasinskipatryk.database.repositories.ClientRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ClientServiceTest {
    public static final String ANY_SURNAME = "ANY_SURNAME";
    public static final String ANY_NAME = "ANY_NAME";
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
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void shouldThrowExceptionWhenMoreThan1ClientForCarId() {
        //given
        when(clientRepository.findByCarId(eq(carId))).thenReturn(List.of(client1, client2));

        //when
        Assertions.assertThrows(IllegalStateException.class, () -> clientService.findClientForCarId(carId));

        //then
        verify(clientRepository).findByCarId(eq(carId));
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    public void shouldThrowExceptionWhenNoClientForCarId() {
        //given
        when(clientRepository.findByCarId(eq(carId))).thenReturn(List.of());

        //when
        Assertions.assertThrows(IllegalStateException.class, () -> clientService.findClientForCarId(carId));

        //then
        verify(clientRepository).findByCarId(eq(carId));
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    public void shouldReturnClientForCarId() {
        //given
        when(clientRepository.findByCarId(eq(carId))).thenReturn(List.of(client1));

        //when
        var actual = clientService.findClientForCarId(carId);

        //then
        Assertions.assertEquals(client1, actual);
        verify(clientRepository).findByCarId(eq(carId));
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    public void shouldReturnClientForNameAndSurname() {
        //given
        when(clientRepository.findByNameAndSurname(eq(ANY_NAME), eq(ANY_SURNAME))).thenReturn(List.of(client1));

        //when
        var actual = clientService.findByNameAndSurname(ANY_NAME, ANY_SURNAME);

        //then
        Assertions.assertEquals(client1, actual);
        verify(clientRepository).findByNameAndSurname(ANY_NAME, ANY_SURNAME);
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    public void shouldThrowExceptionWhenMoreThan1ClientWithNameAndSurname() {
        //given
        when(clientRepository.findByNameAndSurname(eq(ANY_NAME), eq(ANY_SURNAME))).thenReturn(List.of(client1, client1));

        //when
        Assertions.assertThrows(IllegalStateException.class,
                () -> clientService.findByNameAndSurname(ANY_NAME, ANY_SURNAME));

        //then
        verify(clientRepository).findByNameAndSurname(ANY_NAME, ANY_SURNAME);
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    public void shouldReturnNullWhenNoClientWithNameAndSurname() {
        //given
        when(clientRepository.findByNameAndSurname(eq(ANY_NAME), eq(ANY_SURNAME))).thenReturn(List.of());

        //when
        var actual = clientService.findByNameAndSurname(ANY_NAME, ANY_SURNAME);

        //then
        Assertions.assertNull(null, String.valueOf(actual));
        verify(clientRepository).findByNameAndSurname(ANY_NAME, ANY_SURNAME);
        verifyNoMoreInteractions(clientRepository);
    }
}