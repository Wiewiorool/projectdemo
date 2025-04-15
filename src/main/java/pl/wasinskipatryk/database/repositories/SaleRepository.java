package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.SaleEntity;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity,Long> {
}
