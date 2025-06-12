package pl.wasinskipatryk.database.enitities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "Sale")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class SaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private long saleId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dealer_id")
    private DealerEntity dealer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private CarEntity car;

    @Column(name = "sale_date")
    private Instant date;

    @Column(name = "sell_car_price")
    private BigDecimal sellCarPrice;

    @Override
    public String toString() {
        return "SaleEntity{" +
               "saleId=" + saleId +
               ", dealer=" + dealer +
               ", clientId=" + client +
               ", car=" + car +
               ", sale_date=" + date +
               ", sellCarPrice=" + sellCarPrice +
               '}';
    }
}
