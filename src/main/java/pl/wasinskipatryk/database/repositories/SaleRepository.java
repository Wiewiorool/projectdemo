package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.SaleEntity;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
    @Query(value = "SELECT s.* FROM Sale s INNER JOIN Client c ON s.Client_id = c.Client_id  ", nativeQuery = true)
    List<SaleEntity> findUsersSale();
}
