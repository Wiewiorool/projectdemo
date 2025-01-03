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

    @Override
    public List<Car> findAll() {
        Path path = Paths.get("car_details.csv");
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
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
        Path path1 = Paths.get("car_price.csv");
        List<String> linesOne = new ArrayList<>();
        try {
            linesOne = Files.readAllLines(path1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < linesOne.size(); i++) {
            String lineOne = linesOne.get(i);
            String[] elementsOne = lineOne.split(",");
            CarPrice carPrice = CarPrice.builder()
                    .id(Integer.parseInt(elementsOne[0]))
                    .buyPrice(BigDecimal.valueOf(Integer.parseInt(elementsOne[1])))
                    .sellPrice(BigDecimal.valueOf(Integer.parseInt(elementsOne[2])))
                    .build();


        }
        System.out.println(lines);
        System.out.println(linesOne);
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
