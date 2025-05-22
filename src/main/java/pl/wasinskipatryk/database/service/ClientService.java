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
    private final ClientRepository clientRepository;

    public ClientService(@Autowired ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientEntity findClientForCarId(Long carId) {
        List<ClientEntity> clients = clientRepository.findByCarId(carId);

        if (clients.isEmpty()) {
            log.info("Client not found for that car id " + clients);
            throw new IllegalStateException("Client not found");
        }
        if (clients.size() > 1) {
            log.info("Found more than one client for that car id " + clients);
            throw new IllegalStateException("Found more than one client");
        }
        return clients.getFirst();
    }
}
