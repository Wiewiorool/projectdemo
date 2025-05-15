package pl.wasinskipatryk.database.enitities;


import jakarta.persistence.*;
import lombok.*;


@Table(name = "Car_details")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CarDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_details_id")
    private long carDetailsId;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "color")
    private String color;

    @Column(name = "production_year")
    private int productionYear;

    @Column(name = "horse_power")
    private int horsePower;

    @Column(name = "number_of_doors")
    private int numberOfDoors;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_of_car_id")
    private TypeOfCarEntity typeOfCar;

    @Override
    public String toString() {
        return "CarDetailsEntity{" +
               "carDetailsId=" + carDetailsId +
               ", modelName='" + modelName + '\'' +
               ", color='" + color + '\'' +
               ", productionYear=" + productionYear +
               ", horsePower=" + horsePower +
               ", numberOfDoors=" + numberOfDoors +
               ", typeOfCar=" + typeOfCar +
               '}';
    }
}
