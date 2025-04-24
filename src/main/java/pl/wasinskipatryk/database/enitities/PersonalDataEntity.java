package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;
import lombok.Builder;

@Table(name="Personal_data")
@Entity
@Builder

public class PersonalDataEntity {
    @Id
    @Column(name="personal_data_id")
    private long personalDataId;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

}
