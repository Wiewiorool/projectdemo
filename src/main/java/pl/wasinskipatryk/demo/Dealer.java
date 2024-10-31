package pl.wasinskipatryk.demo;

import java.math.BigDecimal;

public class Dealer {
    private String name;
    private String surname;
    private DegreeDealer degreeDealer;
    private SaleRepository saleRepository;

    private Dealer(DealerBuilder dealerBuilder) {
        this.name = dealerBuilder.name;
        this.surname = dealerBuilder.surname;
        this.degreeDealer = dealerBuilder.degreeDealer;
        this.saleRepository = dealerBuilder.saleRepository;
    }

    public void sellCar(Client client, Car car, BigDecimal clientPrice) {
        Sale sale = Sale.builder()
                .car(car)
                .provision(clientPrice.subtract(car.getCarSellPrice()))
                .whoBought(client)
                .whoSold(this)
                .build();
        saleRepository.createNewSale(sale);
    }

    public static class DealerBuilder {
        private String name;
        private String surname;
        private DegreeDealer degreeDealer;

        public DealerBuilder setSaleRepository(SaleRepository saleRepository) {
            this.saleRepository = saleRepository;
            return this;
        }

        private SaleRepository saleRepository;

        public DealerBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public DealerBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public DealerBuilder setDegreeDealer(DegreeDealer degreeDealer) {
            this.degreeDealer = degreeDealer;
            return this;
        }

        public Dealer build() {
            return new Dealer(this);
        }

    }
}
