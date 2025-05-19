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
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class SalesService {

    private ClientRepository clientRepository;
    private SaleRepository saleRepository;
    private CarRepository carRepository;
    private DealerRepository dealerRepository;
    // you may change the fields. Remember to write tests.


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

        // This method will save a new deal. --> na koncu
        //     * Dealer must be found in the system. --> 1. szukamy dealera o id dealerId w bazie danych. Przypisujemy do
        //     zmiennej.
        //     * Client must be created and saved as a new client. --> 2. tworzymy nowego klienta (clientEntity). Zapisujemy go
        //     do bazy danych. Przypisujemy wynik (zapisania do bazy danych) do zmiennej.
        //     * Car must be found in the system. -> 3. szukamy cara o id carId w bazie danych. Przypisujemy do zmiennej.
        //     * Using all the data, a new sale should be created and saved into the database. --> 5. tworzymy nowe
        //     saleEntity i zapisujemy go do bazy danych -> saleRepository.save(newSale);
        //     * Date of the sale is assumed to be equal to the current time when the method will be run. ---> 4. tworzymy
        //     zmienną dla date. Przypisujemy do niej wartość aktualnego czasu* (* - jak poznać aktualny czas w kodzie)

        // 1. szukamy dealera o id dealerId w bazie danych.
        Optional<DealerEntity> dealerEntityOptional = dealerRepository.findById(dealerId);
        // 1a. jeśli nie istnieje to rzucamy wyjątkiem, bo powinien
        if (dealerEntityOptional.isEmpty()) {
            log.info("dealer " + dealerId + " was supposed to be int he system but can't find him");
            throw new IllegalArgumentException("Dealer not found");
        }
        // 1b. Przypisujemy do zmiennej
        DealerEntity dealerEntity = dealerEntityOptional.get();

        // 3. szukamy cara o id carId w bazie danych. Przypisujemy do zmiennej.
        CarEntity carEntity = carRepository.findById(carId).get();

        // 2.  tworzymy nowego klienta (clientEntity).
        //  Przypisujemy wynik (zapisania do bazy danych) do zmiennej.
        ClientEntity clientEntity = addNewClient(clientName, clientSurname, carEntity);

        // 4. tworzymy  zmienną dla date. Przypisujemy do niej wartość aktualnego czasu*
        Instant date = Instant.now();
//        LocalDate
//        LocalTime
//        ZonedDateTime
//        Instant
//        Date
//        LocalDateTime

        // 5. obliczamy cenę sprzedażową auta
        BigDecimal sellCarPrice = carEntity.getBuyCarPrice().multiply(BigDecimal.valueOf(commission));

        // 6. tworzymy nowe saleEntity i zapisujemy go do bazy danych -> saleRepository.save(newSale);
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

    public ClientEntity addNewClient(String clientName, String clientSurname, CarEntity carEntity) {
        ClientEntity newClient = ClientEntity.builder()
                .previouslyOwnedCars(0)
                .car(carEntity)
                .personalData(PersonalDataEntity.builder()
                        .name(clientName)
                        .surname(clientSurname)
                        .build())
                .build();
        // Zapisujemy go do bazy danych.
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
    public SaleEntity getSaleByClientSurname(String clientName, String clientSurname, CarEntity carEntity, BigDecimal sellCarPrice, DealerEntity dealerEntity) {
        // 1. Szukamy klienta o danym nazwisku w bazie danych.
        Collection<ClientEntity> optionalClientEntity = clientRepository.findAllUsers(clientSurname);

        //1a Sprawdzamy czy klient o danym nazwisku istnieje. Nie istnieje więc wyrzucamy exception
        if (optionalClientEntity.isEmpty()) {
            log.info("Client doesnt exist");
            throw new IllegalArgumentException("Client not found");
        }
        //2 Stworzenie nowego clienta + zapisanie go w bazie danych. Użyłem metody już wcześniej stworzonej.
        ClientEntity clientOne = addNewClient(clientName, clientSurname, carEntity);
        clientRepository.save(clientOne); // czy tutaj musi być save? W metodzie w sumie juz mamy jedno save?
        log.info("Add new client{} in to data base", clientOne);

        //3. Jeżeli istnieje wiecej niz 1 client o tym sammy nazwisku.
        if (optionalClientEntity.size() < 2) {
            log.info("Its more than one client with surname: " + clientSurname);
            throw new IllegalStateException("Client found");

        }
        //4. Jezeli istnieje tylko client z 1 salem, returnujemy sale
        if (optionalClientEntity.size() == 1) {
            log.info("Found a client");
        }
        // 5. tworzymy  zmienną dla date. Przypisujemy do niej wartość aktualnego czasu*
        Instant date = Instant.now();

        //6 Tworzymy nowego dealera

        SaleEntity saleEntity = SaleEntity.builder()
                .dealer(dealerEntity)
                .client(clientOne)
                .car(carEntity)
                .date(date)
                .sellCarPrice(sellCarPrice)
                .build();
        SaleEntity savedNewSale = saleRepository.save(saleEntity);
        String surname = savedNewSale.getClient().getPersonalData().getSurname();
        log.info("Saved new sale for client " + surname);
        return getSaleInfo(saleEntity);
    }

    //7 Stworzenie metoy zeby mozna bylo zwrocic stringa w glownej metodzie getSaleByClientSurname
    public SaleEntity getSaleInfo(SaleEntity saleEntity) {
        log.info(saleEntity.getSaleId() + " " + saleEntity.getClient().getPersonalData().getSurname());
        return saleEntity;
    }

}
