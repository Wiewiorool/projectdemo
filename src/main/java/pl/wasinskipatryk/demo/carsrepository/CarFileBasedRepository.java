package pl.wasinskipatryk.demo.carsrepository;

import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarDetails;
import pl.wasinskipatryk.demo.car.CarPrice;
import pl.wasinskipatryk.demo.car.TypeOfCar;
import pl.wasinskipatryk.demo.cardetailsrepository.CarDetailsRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static pl.wasinskipatryk.demo.cardetailsrepository.CarDetailsFileBasedRepository.CAR_DETAILS_CSV_FILE_NAME;

public class CarFileBasedRepository implements CarsRepository {

    public static final String CAR_PRICE_CSV_FILE_NAME = "car_price.csv";
    public static final String CAR_FILE_NAME = "car.csv";

    private CarDetailsRepository carDetailsRepository;

    public CarFileBasedRepository(CarDetailsRepository carDetailsRepository) {
        this.carDetailsRepository = carDetailsRepository;
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();

        Path carDetailsPath = Paths.get(CAR_DETAILS_CSV_FILE_NAME);
        Path path1 = Paths.get(CAR_PRICE_CSV_FILE_NAME);
        Path path2 = Paths.get(CAR_FILE_NAME);
        List<String> carPriceLines = new ArrayList<>();
        List<String> carDetailsLines = new ArrayList<>();
        List<String> carLines = new ArrayList<>();
        try {
            carDetailsLines = Files.readAllLines(carDetailsPath);
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
        for (Car car : allCars) {
            if (car.getCarDetails().getModelName().equalsIgnoreCase(modelName)) {
                return car;
            }
        }
        return null;

    }

    @Override
    public boolean add(Car carToBeAdded) {

        int newIdForCar = findMaxCurrentId() + 1;
        String carToBeAdded1 = newIdForCar + ",";

        int carDetailsId = this.carDetailsRepository.addCarDetails(carToBeAdded.getCarDetails());
        carToBeAdded1 += carDetailsId + ",";

        int carPriceId = addCarPrice(carToBeAdded);
        carToBeAdded1 += carPriceId + ",";

        carToBeAdded1 += "\n";
        saveNewCar(carToBeAdded1, newIdForCar);

        return false;
    }

    private int addCarPrice(Car carToBeAdded) {
        CarPrice carPriceOfTheCar = carToBeAdded.getCarPrice();
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

    private static void saveNewCar(String carToBeSaved, int newIdForCar) {
        Path carPricePath = Paths.get(CAR_FILE_NAME);
        try {
            Files.writeString(carPricePath, carToBeSaved, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Nie udało się wpisać " + e.getMessage());
        }

        System.out.println("Saved new car with id: " + newIdForCar);
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




    // znajduje NAJ
    private int findMaxCurrentId() {
        List<Car> allCars = findAll();
        int max = 0;
        for (int i = 0; i < allCars.size(); i++) {
            Car car = allCars.get(i);
            if (car.getId() > max) {
                max = car.getId();
            }
        }
        return max;
    }



    private CarPrice findCarPrice(CarPrice toBeFound) {
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



    private List<CarPrice> findAllCarPrice() {
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

    @Override
    public void update(Car oldCar, Car newCar) {

    }

    @Override
    public void delete(Car car) {

    }
}
