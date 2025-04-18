package pl.wasinskipatryk.database.enitities;


import jakarta.persistence.*;
import lombok.Builder;

@Table(name="Client")
@Entity
@Builder

public class ClientEntity {
    @Id
    @Column(name="client_id")
    private long clientId;

    @Column(name="owned_cars")
    private long ownedCars;

    @OneToOne
    @JoinColumn(name="car_id", referencedColumnName = "car_id")
    private CarEntity car;

    @OneToOne
    @JoinColumn(name="personal_data_id", referencedColumnName = "personal_data_id")
    private PersonalDataEntity personalData;
}
