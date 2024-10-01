package pl.wasinskipatryk.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        /*SpringApplication.run(DemoApplication.class, args);*/
        Dealer dealer = new Dealer("Jan", "Kowalski", DegreeDealer.JUNIOR);
        Client client = new Client("Andrzej", "Rodowicz", 4);
        Car car = new Car("Audi", 2014, "Black", new BigDecimal(20341), new BigDecimal(24000), 5, 200, TypeOfCar.KOMBI);
    }

}
