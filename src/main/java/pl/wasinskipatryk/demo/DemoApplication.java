package pl.wasinskipatryk.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        /*SpringApplication.run(DemoApplication.class, args);*/
        Dealer dealer = new Dealer.DealerBuilder()
                //.setSaleRepository(new ListBasedSaleRepository(new ListBasedCarsRepository()))
                .setDegreeDealer(DegreeDealer.JUNIOR)
                .setSurname("Lolek")
                .setName("Bolek")
                .build();
        Car car = new Car(new CarPrice.CarPriceBuilder()
                .setBuyPrice(new BigDecimal(24000))
                .setSellPrice(new BigDecimal(30000))
                .build(),
                CarDetails.builder()
                        .modelName("Audi a4")
                        .typeOfCar(TypeOfCar.KOMBI)
                        .color("black")
                        .horsePower(220)
                        .numberOfDoors(5)
                        .productionYear(2014)
                        .build()
        );
        Client client = new Client.ClientBuilder()
                .setName("Adam")
                .setOwnedCars(2)
                .setSurname("Kowalski")
                .build();

        dealer.sellCar(client, car, BigDecimal.valueOf(30000));

    }
}
