package pl.wasinskipatryk;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.wasinskipatryk.database.enitities.*;
import pl.wasinskipatryk.database.repositories.DealerRepository;
import pl.wasinskipatryk.database.repositories.SaleRepository;
import pl.wasinskipatryk.database.service.CarService;
import pl.wasinskipatryk.database.service.ClientService;
import pl.wasinskipatryk.database.service.SalesService;
import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarDetails;
import pl.wasinskipatryk.demo.car.CarPrice;
import pl.wasinskipatryk.demo.car.TypeOfCar;
import pl.wasinskipatryk.demo.cardetailsrepository.CarDetailsFileBasedRepository;
import pl.wasinskipatryk.demo.cardetailsrepository.CarDetailsRepository;
import pl.wasinskipatryk.demo.carpricerepository.CarPriceFileBasedRepository;
import pl.wasinskipatryk.demo.carpricerepository.CarPriceRepository;
import pl.wasinskipatryk.demo.carsrepository.CarFileBasedRepository;
import pl.wasinskipatryk.demo.carsrepository.CarsRepository;
import pl.wasinskipatryk.demo.carsrepository.ListBasedCarsRepository;
import pl.wasinskipatryk.demo.client.Client;
import pl.wasinskipatryk.demo.dealer.Dealer;
import pl.wasinskipatryk.demo.dealer.DegreeDealer;
import pl.wasinskipatryk.demo.salesrepository.ListBasedSaleRepository;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    private static final String nameNoName = "NoName";
    private static final String surnameNoName = "NoName";
    private static final double margin = 1.8;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        SaleRepository saleRepository = context.getBean(SaleRepository.class);


        PersonalDataEntity personalDataEntityDealer = PersonalDataEntity.builder()
                .name("Maria")
                .surname("Wojcik")
                .build();
        DealerEntity dealerEntity = DealerEntity.builder()
                .degree("degree2")
                .personalData(personalDataEntityDealer)
                .sales(List.of())
                .build();

        CarEntity carEntity = CarEntity.builder()
                .buyCarPrice(BigDecimal.valueOf(10_000))
                .carDetails(CarDetailsEntity.builder()
                        .color("Pink")
                        .typeOfCar(TypeOfCarEntity.builder()
                                .value("SEDAN")
                                .build())
                        .numberOfDoors(5)
                        .modelName("VW")
                        .productionYear(1999)
                        .horsePower(250)
                        .build())
                .build();


        CarService carService = context.getBean(CarService.class);
        long newCarId = carService.addNewCar("VW", "Pink", 1999,
                250, 5, TypeOfCar.SEDAN, BigDecimal.valueOf(10_000));
        System.out.println(newCarId);

        DealerRepository dealerRepository = context.getBean(DealerRepository.class);
        DealerEntity dealer1 = dealerRepository.save(dealerEntity);

        ClientService clientService = context.getBean(ClientService.class);
        ClientEntity findClient = clientService.findByNameAndSurname("James", "Aquarius", carEntity);
        ClientEntity clientEntity1 = clientService.findClientForCarId(newCarId);
        System.out.println(findClient);
        System.out.println(clientEntity1);

        SalesService salesService = context.getBean(SalesService.class);
        long newSaleId = salesService.registerNewSale(dealer1.getDealerId(),
                nameNoName, surnameNoName, newCarId, margin);
        System.out.println(saleRepository.findById(newSaleId).get());


        SaleEntity sale = salesService.getSaleByClientSurname("Aquarius");
        System.out.println(sale);


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
