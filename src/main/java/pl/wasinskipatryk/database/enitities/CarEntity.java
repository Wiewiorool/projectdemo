package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Table(name="Car")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_id")
    private long carId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="car_details_id", referencedColumnName = "car_details_id")
    private CarDetailsEntity carDetails;

    @Column(name="buy_car_price")
    private BigDecimal buyCarPrice;
}


