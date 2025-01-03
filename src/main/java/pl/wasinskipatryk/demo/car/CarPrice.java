package pl.wasinskipatryk.demo.car;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class CarPrice {
    private int id;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;

    public BigDecimal getSellPrice(){
        return sellPrice;
    }
    public BigDecimal getBuyPrice(){
        return buyPrice;
    }


    @Override
    public String toString() {
        return "CarPrice{" +
                "buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                '}';
    }
}
