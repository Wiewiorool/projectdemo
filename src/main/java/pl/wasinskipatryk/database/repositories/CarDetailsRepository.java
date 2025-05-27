package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.CarDetailsEntity;

import java.util.List;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetailsEntity,Long> {
/*    @Query(value = """
            SELECT d.* , t.value FROM Car_details d
            JOIN Type_of_car t ON d.type_of_car_id = t.type_of_car_id
            WHERE t.value = ;TypeOfCar
            """,nativeQuery = true)*/

   /* List<CarDetailsEntity> findTypeOfCarById(@Param("typeOfCar"));
    @Query(value = """
            SELECT d.* FROM Car_details d WHERE d.car_details_id = :carDetailsId
            """,nativeQuery = true);
    List<CarDetailsEntity>*/
}
