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

public class FileBasedRepository implements CarsRepository {

    public static final String CAR_PRICE_CSV_FILE_NAME = "car_price.csv";
    public static final String CAR_DETAILS_CSV_FILE_NAME = "car_details.csv";

    @Override
    public List<Car> findAll() {
        Path path = Paths.get(CAR_DETAILS_CSV_FILE_NAME);
        List<String> carDetailsLines = new ArrayList<>();
        try {
            carDetailsLines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Exception during reading file: " + CAR_DETAILS_CSV_FILE_NAME + "Cause:  " + e.getMessage());
        }
        for (int i = 0; i < carDetailsLines.size(); i++) {
            String line = carDetailsLines.get(i);
            String[] elements = line.split(",");
            CarDetails carDetails = CarDetails.builder()
                    .id(Integer.parseInt(elements[0]))
                    .modelName(elements[1])
                    .productionYear(Integer.parseInt(elements[2]))
                    .color(elements[3])
                    .numberOfDoors(Integer.parseInt(elements[4]))
                    .horsePower(Integer.parseInt(elements[5]))
                    .typeOfCar(TypeOfCar.valueOf(elements[6]))
                    .build();

        }
        Path path1 = Paths.get(CAR_PRICE_CSV_FILE_NAME);
        List<String> carPriceLines = new ArrayList<>();
        try {
            carPriceLines = Files.readAllLines(path1);
        } catch (IOException e) {
            System.out.println("Exception during reading file: " + CAR_PRICE_CSV_FILE_NAME + "Cause:  " + e.getMessage());
        }
        for (int i = 0; i < carPriceLines.size(); i++) {
            String line1 = carPriceLines.get(i);
            String[] elementsOne = line1.split(",");
            CarPrice carPrice = CarPrice.builder()
                    .id(Integer.parseInt(elementsOne[0]))
                    .buyPrice(BigDecimal.valueOf(Integer.parseInt(elementsOne[1])))
                    .sellPrice(BigDecimal.valueOf(Integer.parseInt(elementsOne[2])))
                    .build();


        }
        System.out.println(carDetailsLines);
        System.out.println(carPriceLines);
        /////
        return List.of();
    }


    @Override
    public Car findByModelName(String modelName) {
        return null;
    }

    @Override
    public boolean add(Car car) {
        return false;
    }

    @Override
    public void update(Car oldCar, Car newCar) {

    }

    @Override
    public void delete(Car car) {

    }
}
