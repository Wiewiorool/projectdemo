package pl.wasinskipatryk.database.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.wasinskipatryk.database.enitities.CarEntity;
import pl.wasinskipatryk.database.enitities.ClientEntity;
import pl.wasinskipatryk.database.enitities.DealerEntity;
import pl.wasinskipatryk.database.enitities.SaleEntity;
import pl.wasinskipatryk.database.repositories.CarRepository;
import pl.wasinskipatryk.database.repositories.ClientRepository;
import pl.wasinskipatryk.database.repositories.DealerRepository;
import pl.wasinskipatryk.database.repositories.SaleRepository;

import java.util.Optional;

import static org.mockito.Mockito.when;

class SalesServiceUnitTest {

    private SalesService salesService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private DealerRepository dealerRepository;

    @Mock
    private DealerEntity dealerEntity;

    @Mock
    private CarEntity carEntity;

    @Mock
    private ClientEntity clientEntity;

    @Mock
    private SaleEntity saleEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        salesService = new SalesService(clientRepository, saleRepository, carRepository, dealerRepository);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void shouldRegisterNewSale() {
        //given
        long dealerId = 1;
        String clientName = "Adam";
        String clientSurname = "Smith";
        double commission = 1.8;
        long carId = 1;
        long expectedNewSaleId = 5;
        Optional<DealerEntity> dealerEntityOptional = dealerRepository.findById(dealerId);


        //when
        long newSaleId = salesService.registerNewSale(dealerId, clientName, clientSurname, carId, commission);

        //then
        Assertions.assertEquals(expectedNewSaleId, newSaleId);
    }

    @Test
    void shouldNotRegisterNewSaleIfDealerDoNotExist() {
        //given
        long dealerId = 12;
        String clientName = "Adam";
        String clientSurname = "Smith";
        long carId = 1;
        double commission = 1.8;

        when(dealerRepository.findById(dealerId)).thenReturn(Optional.empty());

        //when

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            salesService.registerNewSale(dealerId, clientName, clientSurname, carId, commission);
        });
    }


    @Test
    void getSaleByClientSurname() {
    }
}