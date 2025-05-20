package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.ClientEntity;

import java.util.Collection;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    @Query(value = "SELECT c.* FROM Client c INNER JOIN Personal_data p ON c.personal_data_id = p.personal_data_id WHERE p.surname = :clientSurname", nativeQuery = true)
    Collection<ClientEntity> findAllUsers(@Param("clientSurname") String surname);
}
