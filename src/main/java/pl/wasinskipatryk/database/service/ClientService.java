package pl.wasinskipatryk.database.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wasinskipatryk.database.enitities.ClientEntity;
import pl.wasinskipatryk.database.repositories.ClientRepository;

import java.util.List;

@Slf4j
@Service

public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(
            @Autowired ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientEntity client(Long carId) {
        List<ClientEntity> clientsCarId = clientRepository.findCarId(carId);

        if (clientsCarId.isEmpty()) {
            log.info("Client not found for that car id " + clientsCarId);
            throw new IllegalStateException("Client not found");
        } else if (clientsCarId.size() == 1) {
            return clientsCarId.getFirst();
        } else {
            log.info("Found more than one client for that car id " + clientsCarId);
            throw new IllegalStateException("Found more than one client");
        }

    }

}
