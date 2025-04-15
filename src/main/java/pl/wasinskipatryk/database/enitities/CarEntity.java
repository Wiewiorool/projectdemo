package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Table(name="Car")
@Entity
public class CarEntity {
    @Id
    @Column(name="car_id")
    private long carId;

    @OneToOne
    @JoinColumn(name="car_details_id")
    private long carDetailsId;

    @Column(name="buy_car_price")
    private BigDecimal buyCarPrice;
}
