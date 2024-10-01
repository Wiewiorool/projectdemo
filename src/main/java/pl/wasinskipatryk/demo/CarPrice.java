package pl.wasinskipatryk.demo;

import java.math.BigDecimal;

public class CarPrice {
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;

    public CarPrice(BigDecimal buyPrice, BigDecimal sellPrice) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}
