package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.ClientEntity;
import pl.wasinskipatryk.database.enitities.SaleEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
    @Query(value = "SELECT s.* FROM Sale s WHERE s.client_id = :clientid", nativeQuery = true)
    List<SaleEntity> findUsersSale(@Param("clientid")Long clientId);

}
