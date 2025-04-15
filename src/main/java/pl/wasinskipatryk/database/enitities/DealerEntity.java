package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;

@Table(name="Dealer")
@Entity
public class DealerEntity {
    @Id
    @Column(name="dealer_id")
    private long dealerId;
    @Column(name="degree")
    private String degree;
    @OneToOne
    @JoinColumn(name="personal_data_id")
    private long personalDataId;
    @ManyToOne
    @JoinColumn(name="sale_id")
    private long saleId;
}
