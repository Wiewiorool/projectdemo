package pl.wasinskipatryk.demo.carsrepository;

import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarDetails;
import pl.wasinskipatryk.demo.car.CarPrice;
import pl.wasinskipatryk.demo.car.TypeOfCar;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileBasedRepository implements CarsRepository {

    public static final String CAR_PRICE_CSV_FILE_NAME = "car_price.csv";
    public static final String CAR_DETAILS_CSV_FILE_NAME = "car_details.csv";
    public static final String CAR_FILE_NAME = "car.csv";

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();

        Path path = Paths.get(CAR_DETAILS_CSV_FILE_NAME);
        Path path1 = Paths.get(CAR_PRICE_CSV_FILE_NAME);
        Path path2 = Paths.get(CAR_FILE_NAME);
        List<String> carPriceLines = new ArrayList<>();
        List<String> carDetailsLines = new ArrayList<>();
        List<String> carLines = new ArrayList<>();
        try {
            carDetailsLines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Exception during reading file: " + CAR_DETAILS_CSV_FILE_NAME + " Cause:  " + e.getCause());
        }
        try {
            carPriceLines = Files.readAllLines(path1);
        } catch (IOException e) {
            System.out.println("Exception during reading file: " + CAR_PRICE_CSV_FILE_NAME + " Cause:  " + e.getCause());
        }
        try {
            carLines = Files.readAllLines(path2);
        } catch (IOException e) {
            System.out.println("Exception during reading file: " + CAR_FILE_NAME + " Cause:  " + e.getCause());
        }

        for (int i = 0, j = 0, k = 0; k < carLines.size(); i++, j++, k++) {
            String line2 = carLines.get(k);
            String[] elementsTwo = line2.split(",");

            int carPriceId = Integer.parseInt(elementsTwo[2]);
            int carDetailsId = Integer.parseInt(elementsTwo[1]);

            String line = carDetailsLines.get(carDetailsId - 1);
            String line1 = carPriceLines.get(carPriceId - 1);

            String[] elements = line.split(",");
            String[] elementsOne = line1.split(",");

            CarDetails carDetails = CarDetails.builder()
                    .id(Integer.parseInt(elements[0]))
                    .modelName(elements[1])
                    .productionYear(Integer.parseInt(elements[2]))
                    .color(elements[3])
                    .numberOfDoors(Integer.parseInt(elements[4]))
                    .horsePower(Integer.parseInt(elements[5]))
                    .typeOfCar(TypeOfCar.valueOf(elements[6]))
                    .build();
            CarPrice carPrice = CarPrice.builder()
                    .id(Integer.parseInt(elementsOne[0]))
                    .buyPrice(BigDecimal.valueOf(Integer.parseInt(elementsOne[1])))
                    .sellPrice(BigDecimal.valueOf(Integer.parseInt(elementsOne[2])))
                    .build();

            Car car = Car.builder()
                    .id(Integer.parseInt(elementsTwo[0]))
                    .carDetails(carDetails)
                    .carPrice(carPrice)
                    .build();

            cars.add(car);

        }

        return cars;
    }

    @Override
    public Car findByModelName(String modelName) {
        List<Car> allCars = findAll();
        for(Car car : allCars){
            if(car.getCarDetails().getModelName().equalsIgnoreCase(modelName)){
                return car;
            }
        }
        return null;

    }

    @Override
    public boolean add(Car car) {
        List<Car> allCars = findAll();;
        Car hyundai = new Car(5, CarDetails.builder()
                .id(5)
                .modelName("Hyundai")
                .typeOfCar(TypeOfCar.KOMBI)
                .color("blue")
                .horsePower(180)
                .numberOfDoors(5)
                .productionYear(2024)
                .build(),
                CarPrice.builder()
                        .id(2)
                        .buyPrice(BigDecimal.valueOf(20000))
                        .sellPrice(BigDecimal.valueOf(30000))
                        .build());
        allCars.add(hyundai);
        System.out.println(allCars);
        return true;
    }

    @Override
    public void update(Car oldCar, Car newCar) {

    }

    @Override
    public void delete(Car car) {

    }
}
