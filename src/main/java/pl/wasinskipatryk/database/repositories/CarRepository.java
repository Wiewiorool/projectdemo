package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
   /* @Query(value = """
            SELECT c.* FROM Car c JOIN Car_details d ON c.car_details_id = d.car_details_id
            """, nativeQuery = true)
    CarRepository findCarDetailsIdForCar(@Param("carDetailsId") long car_details_id);*/

}
