package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.CarDetailsEntity;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetailsEntity,Long> {
}
