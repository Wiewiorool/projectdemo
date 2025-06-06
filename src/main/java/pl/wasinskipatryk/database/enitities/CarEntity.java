package pl.wasinskipatryk.database.enitities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Table(name = "Car")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Car_id_seq")
    @SequenceGenerator(
        name = "Car_id_seq", sequenceName =
        "Car_id_seq", allocationSize = 1)
    @Column(name = "car_id")
    private long carId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_details_id", referencedColumnName = "car_details_id")
    private CarDetailsEntity carDetails;

    @Column(name = "buy_car_price")
    private BigDecimal buyCarPrice;

    @Override
    public String toString() {
        return "CarEntity{" +
               "carId=" + carId +
               ", carDetails=" + carDetails +
               ", buyCarPrice=" + buyCarPrice +
               '}';
    }
}


