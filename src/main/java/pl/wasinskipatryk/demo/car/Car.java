package pl.wasinskipatryk.demo.car;

import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Car {
    private int id;
    @Getter
    private CarDetails carDetails;
    private CarPrice carPrice;

    public BigDecimal getCarSellPrice() {
        return carPrice.getSellPrice();
        //Demeter Law -  nie ogladaj się za siebie
    }
    public BigDecimal getCarBuyPrice(){
        return carPrice.getBuyPrice();
    }

}
