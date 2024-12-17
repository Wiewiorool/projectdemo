package pl.wasinskipatryk.demo;

import lombok.Builder;

import java.math.BigDecimal;


@Builder
public class Sale {
    private Dealer whoSold;
    private Client whoBought;
    private Car car;
    private BigDecimal provision;

    public Car getCar() {
        return car;
    }
}
