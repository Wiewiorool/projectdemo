package pl.wasinskipatryk.database.enitities;


import jakarta.persistence.*;

@Table(name="Client")
@Entity
public class ClientEntity {
    @Id
    @Column(name="client_id")
    private long clientId;

    @Column(name="onwed_cars")
    private long ownedCars;

    @OneToOne
    @JoinColumn(name="card_id")
    private long carId;

    @OneToOne
    @JoinColumn(name="personal_data_id")
    private long personalDataId;
}
