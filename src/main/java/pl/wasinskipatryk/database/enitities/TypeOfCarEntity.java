package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="Type_of_car")
@Entity
public class TypeOfCarEntity {
    @Id
    @Column(name="type_of_car_id")
    private long typeOfCarId;

    @Column(name="value")
    private double value;


}
