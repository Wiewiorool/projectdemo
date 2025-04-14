package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.TypeOfCarEntity;

@Repository
public interface TypeOfCarRepository extends JpaRepository<TypeOfCarEntity,Long> {
}
