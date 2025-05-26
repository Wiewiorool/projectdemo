package pl.wasinskipatryk.database.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wasinskipatryk.database.enitities.*;
import pl.wasinskipatryk.database.repositories.CarRepository;
import pl.wasinskipatryk.database.repositories.ClientRepository;
import pl.wasinskipatryk.database.repositories.DealerRepository;
import pl.wasinskipatryk.database.repositories.SaleRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SalesService {
    private final ClientRepository clientRepository;
    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final DealerRepository dealerRepository;

    public SalesService(
            @Autowired ClientRepository clientRepository,
            @Autowired SaleRepository saleRepository,
            @Autowired CarRepository carRepository,
            @Autowired DealerRepository dealerRepository
    ) {
        this.clientRepository = clientRepository;
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.dealerRepository = dealerRepository;
    }

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
     * @param commission    - commission added to the car's buyPrice.
     *                      Example: if the saloon bought the car for 1000 PLN and commission is 1.8,
     *                      then it means the car was sold in the sale for 1000 * 1.8 = 1800 PLN.
     * @return id of the newly created sale.
     */
    @Transactional
    public long registerNewSale(long dealerId, String clientName, String clientSurname, long carId, double commission) {

        Optional<DealerEntity> dealerEntityOptional = dealerRepository.findById(dealerId);

        if (dealerEntityOptional.isEmpty()) {
            log.info("dealer " + dealerId + " was supposed to be int he system but can't find him");
            throw new IllegalArgumentException("Dealer not found");
        }
        DealerEntity dealerEntity = dealerEntityOptional.get();

        CarEntity carEntity = carRepository.findById(carId).get();

        ClientEntity clientEntity = addNewClient(clientName, clientSurname, carEntity);

        Instant date = Instant.now();

        BigDecimal sellCarPrice = carEntity.getBuyCarPrice().multiply(BigDecimal.valueOf(commission));

        SaleEntity newSale = SaleEntity.builder()
                .dealer(dealerEntity)
                .client(clientEntity)
                .car(carEntity)
                .date(date)
                .sellCarPrice(sellCarPrice)
                .build();
        SaleEntity savedNewSale = saleRepository.save(newSale);
        log.info("saved new sale with id " + savedNewSale.getSaleId());
        return savedNewSale.getSaleId();
    }

    public ClientEntity addNewClient(String clientName,
                                     String clientSurname,
                                     CarEntity carEntity
    ) {
        ClientEntity newClient = ClientEntity.builder()
                .previouslyOwnedCars(0)
                .car(carEntity)
                .personalData(PersonalDataEntity.builder()
                        .name(clientName)
                        .surname(clientSurname)
                        .build())
                .build();
        return clientRepository.save(newClient);
    }

    /**
     * This method should return sale linked to the client with provided surname.
     * Meaning, if client with surname "Kovalski" has 1 sale, this method should return this sale.
     * If such client doesn't exist, the method should throw an exception.
     * If there are more clients with provided surname, the method should throw an exception.
     *
     * @param clientSurname - surname of a new client. This client has never been a client in our saloon so far.
     * @return sale for that client
     * @throws IllegalArgumentException - if no client exists with provided surname
     * @throws IllegalStateException    - if two or more clients exists with provided surname
     */
    public SaleEntity getSaleByClientSurname(String clientSurname) {
        List<ClientEntity> optionalClientEntity = clientRepository.findAllUsers(clientSurname);

        if (optionalClientEntity.isEmpty()) {
            log.info("Client doesnt exist");
            throw new IllegalArgumentException("Client not found");
        } else if
        (optionalClientEntity.size() > 1) {
            log.info("Its more than one client with surname: " + clientSurname);
            throw new IllegalStateException("More than one client");
        } else {
            log.info("Client found");
        }
        ClientEntity client = optionalClientEntity.getFirst();

        List<SaleEntity> usersSale = saleRepository.findUsersSale(client.getClientId());

        if (usersSale.isEmpty()) {
            log.info("No sales found");
            throw new IllegalStateException("No sales found");
        }
        if (usersSale.size() > 1) {
            log.info("More than 1 sale found");
            throw new IllegalStateException("More than 1 sale found");
        }
        return usersSale.getFirst();
    }

}
