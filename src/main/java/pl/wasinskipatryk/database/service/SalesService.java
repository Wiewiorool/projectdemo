package pl.wasinskipatryk.database.service;

import org.springframework.stereotype.Service;
import pl.wasinskipatryk.database.enitities.ClientEntity;
import pl.wasinskipatryk.database.enitities.SaleEntity;
import pl.wasinskipatryk.database.repositories.CarRepository;
import pl.wasinskipatryk.database.repositories.ClientRepository;
import pl.wasinskipatryk.database.repositories.DealerRepository;
import pl.wasinskipatryk.database.repositories.SaleRepository;


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
     * @param commision     - commission added to the car's buyPrice.
     *                      Example: if the saloon bought the car for 1000 PLN and commission is 1.8,
     *                      then it means the car was sold in the sale for 1000 * 1.8 = 1800 PLN.
     * @return id of the newly crested sale.
     */

/*    public CarEntity findBuyCarPrice(BigDecimal buyCarPrice) {
        CarEntity carPrice = carRepository.findBuyCarPrice(buyCarPrice);
        return carPrice;
    }*/
    public void checkIfClientExistIfNotAdd(String clientName, String clientSurname) {
        ClientEntity existingClient = clientRepository.findByNameAndSurname(clientName, clientSurname);
        if (existingClient == null) {
            ClientEntity newClient = new ClientEntity();
            clientRepository.save(newClient);
        }
    }

/*    public void checkDealerId(long dealerId) {
        Optional<DealerEntity> dealerEntity = dealerRepository.findById(dealerId);
    }*/

    public long registerNewSale(long dealerId, String clientName, String clientSurname, long carId,
                                double commission) {
        checkIfClientExistIfNotAdd(clientName, clientSurname);
        dealerRepository.findById(dealerId);
        carRepository.findById(carId);

        SaleEntity saleEntity = new SaleEntity();
        saleEntity.setDealer();


        return 1;
    }


}
