package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.TypeOfCarEntity;

@Repository
public interface TypeOfCarRepository extends JpaRepository<TypeOfCarEntity, Long> {
    @Query(value = """
            SELECT * FROM type_of_car
            WHERE UPPER(type_of_car_value) LIKE UPPER(:typeOfCar)
            ORDER BY type_of_car_id limit 1
            """, nativeQuery = true)
    TypeOfCarEntity findByTypeOfCar(@Param("typeOfCar") String typeOfCar);
}
