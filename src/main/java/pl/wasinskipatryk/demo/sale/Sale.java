package pl.wasinskipatryk.demo.sale;

import lombok.Builder;
import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.client.Client;
import pl.wasinskipatryk.demo.dealer.Dealer;

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
