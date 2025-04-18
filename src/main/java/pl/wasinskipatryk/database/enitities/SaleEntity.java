package pl.wasinskipatryk.database.enitities;


import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;
import javax.xml.crypto.Data;
import java.math.BigDecimal;

@Table(name="Sale")
@Entity
public class SaleEntity {
    @Id
    @Column(name="sale_id")
    private long saleId;

    @ManyToOne
    @MapsId("dealerId")
    @JoinColumn(name="dealer_id", referencedColumnName = "dealer_id")
    private DealerEntity dealer;

    @OneToOne
    @JoinColumn(name="client_id")
    private ClientEntity clientId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private CarEntity car;

    @Column(name="date")
    private Instant date;

    @Column(name="sell_car_price")
    private BigDecimal sellCarPrice;



}
