package pl.wasinskipatryk;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.wasinskipatryk.database.enitities.*;
import pl.wasinskipatryk.database.repositories.*;
import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarDetails;
import pl.wasinskipatryk.demo.car.CarPrice;
import pl.wasinskipatryk.demo.car.TypeOfCar;
import pl.wasinskipatryk.demo.cardetailsrepository.CarDetailsFileBasedRepository;
import pl.wasinskipatryk.demo.cardetailsrepository.CarDetailsRepository;
import pl.wasinskipatryk.demo.carpricerepository.CarPriceFileBasedRepository;
import pl.wasinskipatryk.demo.carpricerepository.CarPriceRepository;
import pl.wasinskipatryk.demo.carsrepository.CarsRepository;
import pl.wasinskipatryk.demo.carsrepository.CarFileBasedRepository;
import pl.wasinskipatryk.demo.carsrepository.ListBasedCarsRepository;
import pl.wasinskipatryk.demo.client.Client;
import pl.wasinskipatryk.demo.dealer.Dealer;
import pl.wasinskipatryk.demo.dealer.DegreeDealer;
import pl.wasinskipatryk.demo.salesrepository.ListBasedSaleRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        ClientRepository clientRepository = context.getBean(ClientRepository.class);
        pl.wasinskipatryk.database.repositories.CarDetailsRepository carDetailsRepository = context.getBean(pl.wasinskipatryk.database.repositories.CarDetailsRepository.class);
        CarRepository carRepository = context.getBean(CarRepository.class);
        DealerRepository dealerRepository = context.getBean(DealerRepository.class);
        PersonalDataRepository personalDataRepository = context.getBean(PersonalDataRepository.class);
        SaleRepository saleRepository = context.getBean(SaleRepository.class);
        TypeOfCarRepository typeOfCarRepository = context.getBean(TypeOfCarRepository.class);

        TypeOfCarEntity typeOfCarEntity = TypeOfCarEntity.builder()
                .value("Sedan")
                .build();

        CarDetailsEntity carDetailsEntity = CarDetailsEntity.builder()
                .color("Black")
                .horsePower(250)
                .modelName("Audi")
                .numberOfDoors(4)
                .productionYear(2024)
                .typeOfCar(typeOfCarEntity)
                .build();

        CarEntity carEntity = CarEntity.builder()
                .carDetails(carDetailsEntity)
                .buyCarPrice(BigDecimal.valueOf(30000))
                .build();

        PersonalDataEntity personalDataEntity = PersonalDataEntity.builder()
                .name("Josef")
                .surname("Kovalski")
                .build();

        ClientEntity clientEntity = ClientEntity.builder()
                .personalData(personalDataEntity)
                .ownedCars(1)
                .car(carEntity)
                .build();

        SaleEntity saleEntity = SaleEntity.builder()
                //.dealer()
                .clientId(clientEntity)
                .car(carEntity)
                .date(Instant.parse("2026-01-01T00:00:00Z"))
                .sellCarPrice(BigDecimal.valueOf(100000))
                .build();

        DealerEntity dealerEntity = DealerEntity.builder()
                .degree("degree")
                //.personalData()
                .sales(List.of(saleEntity))
                .build();

        personalDataRepository.save(personalDataEntity);
        typeOfCarRepository.save(typeOfCarEntity);
        carDetailsRepository.save(carDetailsEntity);
        carRepository.save(carEntity);

        clientRepository.save(clientEntity);

        saleRepository.save(saleEntity);

        dealerRepository.save(dealerEntity);

        System.out.println(clientRepository.findAll());
    }

    private static void versionOne() {
        Dealer dealer = new Dealer.DealerBuilder()
                .setSaleRepository(new ListBasedSaleRepository(new ListBasedCarsRepository()))
                .setDegreeDealer(DegreeDealer.JUNIOR)
                .setSurname("Lolek")
                .setName("Bolek")
                .build();
        Car car = Car.builder()
                .carDetails(
                        CarDetails.builder()
                                //
                                .modelName("Kia")
                                .typeOfCar(TypeOfCar.SEDAN)
                                .color("black")
                                .horsePower(110)
                                .numberOfDoors(2)
                                .productionYear(2014)
                                .build())
                .carPrice(
                        CarPrice.builder()
                                .buyPrice(BigDecimal.valueOf(80000))
                                .sellPrice(BigDecimal.valueOf(90000))
                                .build())
                .build();
        Client client = new Client.ClientBuilder()
                .setName("Adam")
                .setOwnedCars(2)
                .setSurname("Kowalski")
                .build();

        dealer.sellCar(client, car, BigDecimal.valueOf(30000));

        CarDetailsRepository carDetailsRepository = new CarDetailsFileBasedRepository();
        CarPriceRepository carPriceRepository = new CarPriceFileBasedRepository();
        CarsRepository carsRepository = new CarFileBasedRepository(carDetailsRepository, carPriceRepository);


        //System.out.println(carsRepository.findByModelName("toyota"));
        carsRepository.add(car);

        if (carsRepository.findByModelName("Kia") == null) {
            System.out.println(false);
        } else {
            System.out.println(true);
        }
    }
}
