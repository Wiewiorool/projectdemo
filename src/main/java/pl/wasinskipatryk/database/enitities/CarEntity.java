package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;
import lombok.Builder;

import java.math.BigDecimal;


@Table(name="Car")
@Entity
@Builder

public class CarEntity {
    @Id
    @Column(name="car_id")
    private long carId;

    @ManyToOne
    @MapsId("carDetailsId")
    @JoinColumn(name="car_details_id", referencedColumnName = "car_details_id")
    private CarDetailsEntity carDetails;

    @Column(name="buy_car_price")
    private BigDecimal buyCarPrice;
}


