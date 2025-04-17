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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="personal_data_id", referencedColumnName = "personal_data_id")
    private long PersonalDataEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sale_id", referencedColumnName = "sale_id")
    private long saleId;
}
