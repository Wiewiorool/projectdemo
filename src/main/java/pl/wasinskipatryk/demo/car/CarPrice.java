package pl.wasinskipatryk.demo.car;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CarPrice {
    private int id;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;


    @Override
    public String toString() {
        return "CarPrice{" +
                "buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                '}';
    }
}
