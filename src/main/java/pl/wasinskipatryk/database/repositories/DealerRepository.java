package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.DealerEntity;

@Repository
public interface DealerRepository extends JpaRepository<DealerEntity,Long> {
}
