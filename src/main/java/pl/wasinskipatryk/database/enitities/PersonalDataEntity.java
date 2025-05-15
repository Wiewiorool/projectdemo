package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "Personal_data")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonalDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_data_id")
    private long personalDataId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Override
    public String toString() {
        return "PersonalDataEntity{" +
               "personalDataId=" + personalDataId +
               ", name='" + name + '\'' +
               ", surname='" + surname + '\'' +
               '}';
    }
}
