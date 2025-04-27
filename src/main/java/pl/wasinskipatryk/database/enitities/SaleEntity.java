package pl.wasinskipatryk.database.enitities;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;
import javax.xml.crypto.Data;
import java.math.BigDecimal;

@Table(name="Sale")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sale_id")
    private long saleId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="dealer_id")
    private DealerEntity dealer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="client_id")
    private ClientEntity clientId;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="car_id")
    private CarEntity car;

    @Column(name="date")
    private Instant date;

    @Column(name="sell_car_price")
    private BigDecimal sellCarPrice;



}
