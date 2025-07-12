package pl.wasinskipatryk.database.enitities;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "Client")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private long clientId;

    @Column(name = "owned_cars")
    private long previouslyOwnedCars; // number of cars BEFORE making sale

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private CarEntity car; // the car this client is buying

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_data_id", referencedColumnName = "personal_data_id")
    private PersonalDataEntity personalData;

    @Override
    public String toString() {
        return "ClientEntity{" +
               "clientId=" + clientId +
               ", ownedCars=" + previouslyOwnedCars +
               ", car=" + car +
               ", personalData=" + personalData +
               '}';
    }
}
