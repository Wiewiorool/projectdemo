package pl.wasinskipatryk.database.enitities;


import jakarta.persistence.*;
import lombok.*;

@Table(name="Client")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="client_id")
    private long clientId;

    @Column(name="owned_cars")
    private long ownedCars;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="car_id", referencedColumnName = "car_id")
    private CarEntity car;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="personal_data_id", referencedColumnName = "personal_data_id")
    private PersonalDataEntity personalData;
}
