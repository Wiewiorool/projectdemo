package pl.wasinskipatryk.database.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pl.wasinskipatryk.database.enitities.DealerEntity;
import pl.wasinskipatryk.database.enitities.PersonalDataEntity;
import pl.wasinskipatryk.database.enitities.SaleEntity;
import pl.wasinskipatryk.database.repositories.ClientRepository;
import pl.wasinskipatryk.database.repositories.DealerRepository;
import pl.wasinskipatryk.database.repositories.SaleRepository;

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


    @Test
    void registerNewSaleWhenDealerExist() {
        //given
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
                    salesService.registerNewSale(nonExistDealerId, "Adam", "Kolvaksi",
                            5, 1.8);
                }
        );
    }

    @Test
    void getSaleByClientSurname() {
    }
}