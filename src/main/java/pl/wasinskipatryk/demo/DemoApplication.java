package pl.wasinskipatryk.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        /*SpringApplication.run(DemoApplication.class, args);*/
        Dealer dealer = new Dealer.DealerBuilder()
                .setDegreeDealer(DegreeDealer.JUNIOR)
                .setSurname("Lolek")
                .setName("Bolek")
                .build();
        Car car = new Car(new CarPrice.CarPriceBuilder()
                .setBuyPrice(new BigDecimal(24000))
                .setSellPrice(new BigDecimal(30000))
                .build(),
                new CarDetails.CarDetailsBuilder()
                        .setModelName("Audi a4")
                        .setTypeOfCar(TypeOfCar.KOMBI)
                        .setColor("black")
                        .setHorsePower(220)
                        .setNumberOfDoors(5)
                        .setProductionYear(2014)
                        .build()
        );
        Client client = new Client.ClientBuilder()
                .setName("Adam")
                .setOwnedCars(2)
                .setSurname("Kowalski")
                .build();

    }

}
