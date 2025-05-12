package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity,Long> {
    ClientEntity findByNameAndSurname(String name, String surname);
}
