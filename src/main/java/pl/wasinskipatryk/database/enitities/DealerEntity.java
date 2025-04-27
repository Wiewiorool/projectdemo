package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name="Dealer")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class DealerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dealer_id")
    private long dealerId;

    @Column(name="degree")
    private String degree;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="personal_data_id", referencedColumnName = "personal_data_id")
    private PersonalDataEntity personalData;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dealer",cascade = CascadeType.ALL)
    private List<SaleEntity> sales;
}
