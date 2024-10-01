package pl.wasinskipatryk.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        /*SpringApplication.run(DemoApplication.class, args);*/
        Dealer dealer = new Dealer("Jan", "Kowalski", DegreeDealer.JUNIOR);
        Client client = new Client("Andrzej", "Rodowicz", 4);
        Car car = new Car(new CarPrice(new BigDecimal(20000), new BigDecimal(24000)),
                new CarDetails.CarDetailsBuilder()
                        .setModelName("Audi a4")
                        .setTypeOfCar(TypeOfCar.KOMBI)
                        .setColor("black")
                        .setHorsePower(220)
                        .setNumberOfDoors(5)
                        .setProductionYear(2014)
                        .build()
        );
    }

}
