package pl.wasinskipatryk.database.enitities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Year;


@Table(name="Car_details")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CarDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_details_id")
    private long carDetailsId;

    @Column(name="model_name")
    private String modelName;

    @Column(name="color")
    private String color;

    @Column(name="production_year")
    private int productionYear;

    @Column(name="horse_power")
    private int horsePower;

    @Column(name="number_of_doors")
    private int numberOfDoors;

    @OneToOne
    @JoinColumn(name="type_of_car_id")
    private TypeOfCarEntity typeOfCar;

}
