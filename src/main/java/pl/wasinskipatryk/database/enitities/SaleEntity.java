package pl.wasinskipatryk.database.enitities;


import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.math.BigDecimal;

@Table(name="Sale")
@Entity
public class SaleEntity {
    @Id
    @Column(name="sale_id")
    private long saleId;

    @OneToMany
    @JoinColumn(name="dealer_id")
    private long dealerId;

    @OneToMany
    @JoinColumn(name="client_id")
    private long clientId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id", referencedColumnName = "car_id")
    private long carId;

    @Column(name="date")
    private Data date;

    @Column(name="sell_car_price")
    private BigDecimal sellCarPrice;



}
