package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "Type_of_car")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TypeOfCarEntity {
    @Id
    @Column(name = "type_of_car_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Type_of_car_id_seq")
    @SequenceGenerator(
        name = "Type_of_car_id_seq", sequenceName =
        "Type_of_car_id_seq", allocationSize = 1)
    private Long typeOfCarId;

    @Column(name = "value")
    private String value;

}
