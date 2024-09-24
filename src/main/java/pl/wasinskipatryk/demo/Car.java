package pl.wasinskipatryk.demo;

import java.math.BigDecimal;

public class Car {
    private String brand;
    private int productionYear;
    private String color;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;

    public Car(String brand, int productionYear, String color, BigDecimal buyPrice, BigDecimal sellPrice) {
        this.brand = brand;
        this.productionYear = productionYear;
        this.color = color;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}
