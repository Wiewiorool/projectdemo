package pl.wasinskipatryk.demo;

import java.math.BigDecimal;

public class CarPrice {
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;

    private CarPrice(CarPriceBuilder carPriceBuilder) {
        this.buyPrice = carPriceBuilder.buyPrice;
        this.sellPrice = carPriceBuilder.sellPrice;
    }

    @Override
    public String toString() {
        return "CarPrice{" +
                "buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                '}';
    }

    public static class CarPriceBuilder {
        private BigDecimal buyPrice;
        private BigDecimal sellPrice;

        public CarPriceBuilder setSellPrice(BigDecimal sellPrice) {
            this.sellPrice = sellPrice;
            return this;
        }

        public CarPriceBuilder setBuyPrice(BigDecimal buyPrice) {
            this.buyPrice = buyPrice;
            return this;
        }

        public CarPrice build() {
            return new CarPrice(this);
        }

    }
}
