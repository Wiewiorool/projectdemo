package pl.wasinskipatryk.database.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pl.wasinskipatryk.database.enitities.*;
import pl.wasinskipatryk.database.repositories.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SalesServiceTest {

    @Autowired
    private SalesService salesService;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarDetailsRepository carDetailsRepository;


    @Test
    void registerNewSaleWhenDealerExist() {
        //given\
        PersonalDataEntity dealerPersonal = PersonalDataEntity.builder()
                .name("1")
                .surname("2")
                .build();
        DealerEntity dealer = DealerEntity.builder()
                .personalData(dealerPersonal)
                .degree("Master")
                .build();
        DealerEntity saveNewDealer = dealerRepository.save(dealer);
        long dealerId = saveNewDealer.getDealerId();
        //when
        long newSaleId = salesService.registerNewSale(dealerId, "Adam",
                "Smith", 5, 1.8);
        //then
        Optional<SaleEntity> savedSale = saleRepository.findById(newSaleId);
        Assertions.assertTrue(savedSale.isPresent());

    }

    @Test
    void doNotRegisterNewSaleWhenDealerDoNotExist() {
        //given
        long nonExistDealerId = 645L;
        //when

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    salesService.registerNewSale(nonExistDealerId, "Adam", "Kovalski",
                            5, 1.8);
                }
        );
    }

    @Test
    void getSaleByClientSurname() {
        //given
        DealerEntity dealer = DealerEntity.builder()
                .degree("Master")
                .personalData(PersonalDataEntity.builder()
                        .surname("Smith")
                        .name("Joe")
                        .build())
                .build();
        PersonalDataEntity clientPersonal = PersonalDataEntity.builder()
                .surname("Kowalski")
                .name("Andrzej")
                .build();
        CarDetailsEntity details = CarDetailsEntity.builder()
                .color("blue")
                .typeOfCar(TypeOfCarEntity.builder()
                        .value("SEDAN")
                        .build())
                .numberOfDoors(5)
                .modelName("Audi")
                .productionYear(2020)
                .horsePower(144)
                .build();
        CarEntity car = CarEntity.builder()
                .buyCarPrice(BigDecimal.valueOf(10_000))
                .carDetails(details)
                .build();
        ClientEntity client = ClientEntity.builder()
                .car(car)
                .previouslyOwnedCars(0)
                .personalData(clientPersonal)
                .build();
        SaleEntity sale = SaleEntity.builder()
                .client(client)
                .sellCarPrice(BigDecimal.valueOf(15_000))
                .date(Instant.now())
                .car(car)
                .dealer(dealer)
                .build();
        saleRepository.save(sale);

        //when
        Optional<SaleEntity> sales = Optional.ofNullable(salesService.getSaleByClientSurname("Kowalski"));
        //then
        Assertions.assertEquals("Kowalski", sales.get().getClient().getPersonalData().getSurname());
    }

    @Test
    void addNewClient() {
        //given
        //when
        CarEntity car = CarEntity.builder()
                .carDetails(CarDetailsEntity.builder()
                        .horsePower(100)
                        .productionYear(2002)
                        .modelName("BMW")
                        .numberOfDoors(5)
                        .color("Blue")
                        .typeOfCar(TypeOfCarEntity.builder()
                                .value("SEDAN")
                                .build())
                        .build())
                .buyCarPrice(BigDecimal.valueOf(10_000))
                .build();
        PersonalDataEntity clientPersonals = PersonalDataEntity.builder()
                .name("Jaroslaw")
                .surname("Adams")
                .build();
        ClientEntity client = ClientEntity.builder()
                .previouslyOwnedCars(0)
                .car(car)
                .personalData(clientPersonals)
                .build();
        ClientEntity newClient = clientRepository.save(client);
        //then
        Assertions.assertNotNull(newClient);


    }

}