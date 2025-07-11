package pl.wasinskipatryk.database.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wasinskipatryk.database.enitities.ClientEntity;
import pl.wasinskipatryk.database.enitities.PersonalDataEntity;
import pl.wasinskipatryk.database.repositories.ClientRepository;

import java.util.List;


@Slf4j
@Service
public class ClientService {
    private final ClientRepository clientRepository;


    public ClientService(@Autowired ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public ClientEntity findClientForCarId(Long carId) {
        List<ClientEntity> clients = clientRepository.findByCarId(carId);

        if (clients.isEmpty()) {
            log.info("Client not found for that car id " + carId);
            throw new IllegalStateException("Client not found for that carId");
        }
        if (clients.size() > 1) {
            log.info("Found more than one client for that car id " + carId + " " + clients);
            throw new IllegalStateException("Found more than one client");
        }
        return clients.get(0);
    }

    public ClientEntity addNewClient(
            String clientName,
            String clientSurname
    ) {
        ClientEntity newClient = ClientEntity.builder()
                .previouslyOwnedCars(0)
                .personalData(PersonalDataEntity.builder()
                        .name(clientName)
                        .surname(clientSurname)
                        .build())
                .build();
        return newClient;
    }

    public ClientEntity findByNameAndSurname(String clientName, String clientSurname) {

        List<ClientEntity> clients = clientRepository.findByNameAndSurname(clientName, clientSurname);


        if (clients.isEmpty()) {
            log.info("Client not found. ");
            return null;
        }
        if (clients.size() > 1) {
            log.info("Found more than one client with than name and surname " + clients);
            throw new IllegalStateException("Found more than one client");
        }
        return clients.get(0);

    }
}
