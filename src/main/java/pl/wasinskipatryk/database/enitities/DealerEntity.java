package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;
import java.util.List;

@Table(name="Dealer")
@Entity
public class DealerEntity {
    @Id
    @Column(name="dealer_id")
    private long dealerId;

    @Column(name="degree")
    private String degree;

    @OneToOne
    @JoinColumn(name="personal_data_id", referencedColumnName = "personal_data_id")
    private PersonalDataEntity personalData;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dealer")
    private List<SaleEntity> sales;
}
