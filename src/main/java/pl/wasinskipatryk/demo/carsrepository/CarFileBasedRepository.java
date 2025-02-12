package pl.wasinskipatryk.demo.carsrepository;

import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarDetails;
import pl.wasinskipatryk.demo.car.CarPrice;
import pl.wasinskipatryk.demo.car.TypeOfCar;
import pl.wasinskipatryk.demo.cardetailsrepository.CarDetailsRepository;
import pl.wasinskipatryk.demo.carpricerepository.CarPriceRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static pl.wasinskipatryk.demo.cardetailsrepository.CarDetailsFileBasedRepository.CAR_DETAILS_CSV_FILE_NAME;
import static pl.wasinskipatryk.demo.carpricerepository.CarPriceFileBasedRepository.CAR_PRICE_CSV_FILE_NAME;


public class CarFileBasedRepository implements CarsRepository {
    public static final String CAR_FILE_NAME = "car.csv";

    private CarDetailsRepository carDetailsRepository;
    private CarPriceRepository carPriceRepository;

    public CarFileBasedRepository(CarDetailsRepository carDetailsRepository, CarPriceRepository carPriceRepository) {
        this.carDetailsRepository = carDetailsRepository;
        this.carPriceRepository = carPriceRepository;
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

        int carPriceId = this.carPriceRepository.addCarPrice(carToBeAdded.getCarPrice());
        carToBeAdded1 += carPriceId + ",";

        carToBeAdded1 += "\n";
        saveNewCar(carToBeAdded1, newIdForCar);

        return false;
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

    @Override
    public void update(Car oldCar, Car newCar) {

    }

    @Override
    public void delete(Car car) {

    }
}
