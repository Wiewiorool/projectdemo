package pl.wasinskipatryk.demo.car;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@EqualsAndHashCode
@ToString

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
