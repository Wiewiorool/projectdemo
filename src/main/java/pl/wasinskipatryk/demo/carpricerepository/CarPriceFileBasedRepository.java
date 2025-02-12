package pl.wasinskipatryk.demo.carpricerepository;

import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarPrice;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CarPriceFileBasedRepository implements CarPriceRepository {

    public static final String CAR_PRICE_CSV_FILE_NAME = "car_price.csv";

    @Override
    public int addCarPrice(CarPrice carPriceOfTheCar) {
        CarPrice foundOrNotCarPrice = findCarPrice(carPriceOfTheCar);
        if (foundOrNotCarPrice == null) {
            System.out.println("Nie znaleźliśmy wybranego CarPrice, dodajemy nowe CarPrice");
            int newCarPriceId = findMaxCurrentCarPriceId() + 1;
            String carPriceLine = carPriceAsString(newCarPriceId, carPriceOfTheCar);
            saveNewCarPrice(carPriceLine, newCarPriceId);
            return newCarPriceId;
        } else {
            System.out.println("Znaleźliśmy takie CarPrice: " + foundOrNotCarPrice.getId());
            return foundOrNotCarPrice.getId();
        }
    }

    private String carPriceAsString(int carPriceId, CarPrice carPrice) {
        String line = carPriceId + "," + carPrice.getBuyPrice() + "," + carPrice.getSellPrice() + "\n";
        return line;
    }

    private static void saveNewCarPrice(String carPriceToBeSaved, int newCarPriceId) {
        Path carPricePath = Paths.get(CAR_PRICE_CSV_FILE_NAME);
        try {
            Files.writeString(carPricePath, carPriceToBeSaved, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Nie udało się wpisać " + e.getMessage());
        }

        System.out.println("Saved new car price with id: " + newCarPriceId);
    }

    private int findMaxCurrentCarPriceId() {
        List<CarPrice> allCarPrice = findAllCarPrice();
        int max = 0;
        for (int i = 0; i < allCarPrice.size(); i++) {
            CarPrice carPrice = allCarPrice.get(i);
            if (carPrice.getId() > max) {
                max = carPrice.getId();
            }
        }
        return max;
    }

    public CarPrice findCarPrice(CarPrice toBeFound) {
        List<CarPrice> allCarPrice = findAllCarPrice();
        for (int i = 0; i < allCarPrice.size(); i++) {
            CarPrice carPrice = allCarPrice.get(i);
            if (isCarPriceTheSame(carPrice, toBeFound)) {
                return carPrice;
            }


        }
        return null;
    }

    private boolean isCarPriceTheSame(CarPrice carPrice1, CarPrice carPrice2) {
        if (carPrice1.getBuyPrice().equals(carPrice2.getBuyPrice())
                && carPrice1.getSellPrice().equals(carPrice2.getSellPrice())) {
            return true;
        }
        return false;
    }

    public List<CarPrice> findAllCarPrice() {
        List<CarPrice> carPrice = new ArrayList<>();
        Path carPricePath = Paths.get(CAR_PRICE_CSV_FILE_NAME);
        List<String> carPricesLines = new ArrayList<>();
        try {
            carPricesLines = Files.readAllLines(carPricePath);
        } catch (IOException e) {
            System.out.println("Exception during reading file: " + CAR_PRICE_CSV_FILE_NAME + " Cause:  " + e.getCause());
        }
        for (int i = 0; i < carPricesLines.size(); i++) {
            String carPriceAsString = carPricesLines.get(i);
            String[] carPricesAsArray = carPriceAsString.split(",");
            CarPrice carPrice1 = CarPrice.builder()
                    .id(Integer.parseInt(carPricesAsArray[0]))
                    .buyPrice(BigDecimal.valueOf(Integer.parseInt(carPricesAsArray[1])))
                    .sellPrice(BigDecimal.valueOf(Integer.parseInt(carPricesAsArray[2])))
                    .build();
            carPrice.add(carPrice1);
        }
        return carPrice;
    }


}
