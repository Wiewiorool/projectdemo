package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Table(name = "Car_details")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CarDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Car_details_id_seq")
    @SequenceGenerator(
        name = "Car_details_id_seq", sequenceName =
        "Car_details_id_seq", allocationSize = 1)
    @Column(name = "car_details_id")
    private Long carDetailsId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDetailsEntity that = (CarDetailsEntity) o;
        return carDetailsId == that.carDetailsId && productionYear == that.productionYear && horsePower == that.horsePower && numberOfDoors == that.numberOfDoors && Objects.equals(modelName, that.modelName) && Objects.equals(color, that.color) && Objects.equals(typeOfCar, that.typeOfCar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carDetailsId, modelName, color, productionYear, horsePower, numberOfDoors, typeOfCar);
    }
}
