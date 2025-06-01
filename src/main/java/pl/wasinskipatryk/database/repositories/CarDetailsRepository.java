package pl.wasinskipatryk.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wasinskipatryk.database.enitities.CarDetailsEntity;


@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetailsEntity, Long> {
    @Query(value = """
            SELECT *  FROM car_details
            WHERE UPPER(model_name) LIKE UPPER(:modelName)
            And UPPER(color) LIKE UPPER(:color)
            AND horse_power = :horsePower
            AND number_of_doors = :numberOfDoors
            AND production_year = :productionYear
            AND type_of_car_id  = :typeOfCarId
            """, nativeQuery = true)
    CarDetailsEntity findSpecificCarDetails(
            @Param("modelName") String modelName,
            @Param("color") String color,
            @Param("horsePower") int horsePower,
            @Param("numberOfDoors") int numberOfDoors,
            @Param("productionYear") int productionYear,
            @Param("typeOfCarId") long typeOfCarId
    );
}
