package pl.wasinskipatryk.demo;


import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarDetails;
import pl.wasinskipatryk.demo.car.CarPrice;
import pl.wasinskipatryk.demo.car.TypeOfCar;
import pl.wasinskipatryk.demo.carsrepository.CarsRepository;
import pl.wasinskipatryk.demo.carsrepository.FileBasedRepository;
import pl.wasinskipatryk.demo.carsrepository.ListBasedCarsRepository;
import pl.wasinskipatryk.demo.client.Client;
import pl.wasinskipatryk.demo.dealer.Dealer;
import pl.wasinskipatryk.demo.dealer.DegreeDealer;
import pl.wasinskipatryk.demo.salesrepository.ListBasedSaleRepository;

import java.math.BigDecimal;


public class DemoApplication {

    public static void main(String[] args) {
        /*SpringApplication.run(DemoApplication.class, args);*/
        Dealer dealer = new Dealer.DealerBuilder()
                .setSaleRepository(new ListBasedSaleRepository(new ListBasedCarsRepository()))
                .setDegreeDealer(DegreeDealer.JUNIOR)
                .setSurname("Lolek")
                .setName("Bolek")
                .build();
        Car car = new Car(1,
            CarDetails.builder()
                //
                .modelName("Kia")
                .typeOfCar(TypeOfCar.KOMBI)
                .color("black")
                .horsePower(220)
                .numberOfDoors(5)
                .productionYear(2014)
                .build(),
                CarPrice.builder()
                        .id(1)
                        .buyPrice(BigDecimal.valueOf(10000))
                        .sellPrice(BigDecimal.valueOf(20000))
                        .build());
        Client client = new Client.ClientBuilder()
                .setName("Adam")
                .setOwnedCars(2)
                .setSurname("Kowalski")
                .build();

        dealer.sellCar(client, car, BigDecimal.valueOf(30000));

        CarsRepository carsRepository = new FileBasedRepository();
        //System.out.println(carsRepository.findByModelName("toyota"));
        carsRepository.add(car);
        if (carsRepository.findByModelName("Kia") == null) {
            System.out.println(false);
        } else {
            System.out.println(true);
        }
    }
}
