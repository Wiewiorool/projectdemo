package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name="Type_of_car")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TypeOfCarEntity {
    @Id
    @Column(name="type_of_car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long typeOfCarId;

    @Column(name="value")
    private String value;


}
