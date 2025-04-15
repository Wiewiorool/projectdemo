package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;

@Table(name="Personal_data")
@Entity
public class PersonalDataEntity {
    @Id
    @ManyToMany
    @JoinColumn(name="personal_data_id")
    private long personalDataId;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

}
