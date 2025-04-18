package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity,Long> {
}
