package pl.wasinskipatryk.demo;

public class Dealer {
    private String name;
    private String surname;
    private DegreeDealer degreeDealer;

    private Dealer(DealerBuilder dealerBuilder) {
        this.name = name;
        this.surname = surname;
        this.degreeDealer = degreeDealer;
    }

    public static class DealerBuilder {
        private String name;
        private String surname;
        private DegreeDealer degreeDealer;

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
