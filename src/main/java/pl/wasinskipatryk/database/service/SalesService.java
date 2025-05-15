package pl.wasinskipatryk.database.service;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Service;
import pl.wasinskipatryk.database.enitities.*;
import pl.wasinskipatryk.database.repositories.CarRepository;
import pl.wasinskipatryk.database.repositories.ClientRepository;
import pl.wasinskipatryk.database.repositories.DealerRepository;
import pl.wasinskipatryk.database.repositories.SaleRepository;

import java.math.BigDecimal;
import java.time.Instant;


@Service
public class SalesService {

    private ClientRepository clientRepository;
    private SaleRepository saleRepository;
    private CarRepository carRepository;
    private DealerRepository dealerRepository;
    // you may change the fields. Remember to write tests.


    /**
     * This method will save a new deal.
     * Dealer must be found in the system.
     * Client must be created and saved as a new client.
     * Car must be found in the system.
     * Using all the data, a new sale should be created and saved into the database.
     * Date of the sale is assumed to be equal to the current time when the method will be run.
     *
     * @param dealerId      - who made the deal, id of the dealer. Dealer should already exist in our IT system.
     * @param clientName    - first name of a new client. This client has never been a client in our saloon so far.
     * @param clientSurname - surname of a new client. This client has never been a client in our saloon so far.
     * @param carId         - id of the sold car. This car should already exist in our IT system.
     * @param commission     - commission added to the car's buyPrice.
     *                      Example: if the saloon bought the car for 1000 PLN and commission is 1.8,
     *                      then it means the car was sold in the sale for 1000 * 1.8 = 1800 PLN.
     * @return id of the newly created sale.
     */
    public long registerNewSale(long dealerId, String clientName, String clientSurname, long carId, double commission) {

        Optional<DealerEntity> dealerEntity = dealerRepository.findById(dealerId);
        if (dealerEntity.isEmpty()) {
            System.out.println("dealer " + dealerId + " was supposed to be int he system but can't find him");
            throw new IllegalArgumentException("Dealer not found");
        }
        CarEntity carEntity = carRepository.findById(carId).get();
        ClientEntity clientEntity = addNewClient(clientName, clientSurname);

        SaleEntity newSale = SaleEntity.builder()
            .car(carEntity)
            .dealer(dealerEntity)
            .date(Instant.now())
            .clientId(clientEntity)
            .sellCarPrice(carEntity.getBuyCarPrice().multiply(BigDecimal.valueOf(commission)))
            .build();
        saleRepository.save(newSale);


        return newSale.getSaleId();
    }

    public ClientEntity addNewClient(String clientName, String clientSurname) {
        ClientEntity newClient = ClientEntity.builder()
                .personalData(PersonalDataEntity.builder()
                        .name(clientName)
                        .surname(clientSurname)
                        .build())
                .build();
        return clientRepository.save(newClient);
    }




}
