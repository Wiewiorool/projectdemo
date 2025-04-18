package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.PersonalDataEntity;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalDataEntity,Long> {
}
