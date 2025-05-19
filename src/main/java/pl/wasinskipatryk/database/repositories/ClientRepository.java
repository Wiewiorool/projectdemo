package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.ClientEntity;

import java.util.Collection;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    @Query(value = "SELECT c FROM Client c WHERE c.surname = 1", nativeQuery = true)
    Collection<ClientEntity> findAllUsers(String clientSurname);
}
