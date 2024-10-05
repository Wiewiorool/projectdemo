package pl.wasinskipatryk.demo;

public class Client {
    private String name;
    private String surname;
    private int ownedCars;

    private Client(ClientBuilder clientBuilder) {
        this.name = clientBuilder.name;
        this.surname = clientBuilder.surname;
        this.ownedCars = clientBuilder.ownedCars;
    }

    public static class ClientBuilder {
        private String name;
        private String surname;
        private int ownedCars;

        public ClientBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ClientBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public ClientBuilder setOwnedCars(int ownedCars) {
            this.ownedCars = ownedCars;
            return this;
        }

        public Client build() {
            return new Client(this);
        }
    }

}
