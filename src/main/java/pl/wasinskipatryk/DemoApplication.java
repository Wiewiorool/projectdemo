package pl.wasinskipatryk;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.wasinskipatryk.database.enitities.*;
import pl.wasinskipatryk.database.repositories.ClientRepository;
import pl.wasinskipatryk.database.repositories.SaleRepository;
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
import java.time.Instant;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        ClientRepository clientRepository = context.getBean(ClientRepository.class);
        SaleRepository saleRepository = context.getBean(SaleRepository.class);

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

        PersonalDataEntity personalDataEntityDealer = PersonalDataEntity.builder()
                .name("Maria")
                .surname("Wojcik")
                .build();

        PersonalDataEntity personalDataEntityClient = PersonalDataEntity.builder()
                .name("Josef")
                .surname("As")
                .build();

        ClientEntity clientEntity = ClientEntity.builder()
                .personalData(personalDataEntityClient)
                .previouslyOwnedCars(1)
                .car(carEntity)
                .build();


        DealerEntity dealerEntity = DealerEntity.builder()
                .degree("degree2")
                .personalData(personalDataEntityDealer)
                .sales(List.of())
                .build();

        SaleEntity saleEntity = SaleEntity.builder()
                .dealer(dealerEntity)
                .client(clientEntity)
                .car(carEntity)
                .date(Instant.parse("2026-01-01T00:00:00Z"))
                .sellCarPrice(BigDecimal.valueOf(100_000))
                .build();

        SaleEntity newSale1 = saleRepository.save(saleEntity);

        DealerEntity dealer1 = newSale1.getDealer();
        CarEntity car1 = newSale1.getCar();

        SalesService salesService = context.getBean(SalesService.class);
        long newSaleId = salesService.registerNewSale(dealer1.getDealerId(), "NoName", "NoName", car1.getCarId(), 1.8);
        //System.out.println(saleRepository.findById(newSaleId).get());

        /*SaleEntity sale = salesService.getSaleByClientSurname("As");
        System.out.println("Sale ID: " + sale.getSaleId() + " "
                + "Dealer ID: " + sale.getDealer() + " "
                + "Client ID: " + sale.getClient().getClientId() + " "
                + "Car ID: " + sale.getCar().getCarId() + " "
                + "Date: " + sale.getDate() + " "
                + "Sell car price: " + sale.getSellCarPrice() + " "
                + "Client: " + sale.getClient().getPersonalData().getSurname());
        */
        ClientService clientService = context.getBean(ClientService.class);
        ClientEntity clientEntity1 = clientService.findClientForCarId(car1.getCarId());
        System.out.println(clientEntity1);


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
