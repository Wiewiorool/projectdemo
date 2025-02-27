package pl.wasinskipatryk.demo.carsrepository;

import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarDetails;
import pl.wasinskipatryk.demo.car.CarPrice;
import pl.wasinskipatryk.demo.cardetailsrepository.CarDetailsRepository;
import pl.wasinskipatryk.demo.carpricerepository.CarPriceRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


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
        List<String> carLines = readCars();
        List<CarDetails> carDetails = this.carDetailsRepository.findAllCarDetails();
        List<CarPrice> carPrice = this.carPriceRepository.findAllCarPrice();
        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < carLines.size(); i++) {
            Car car = parseCar(carLines.get(i), carDetails, carPrice);
            cars.add(car);
        }
        return cars;
    }

    private static List<String> readCars() {
        Path carPath = Paths.get(CAR_FILE_NAME);
        List<String> carLines = new ArrayList<>();
        try {
            carLines = Files.readAllLines(carPath);
        } catch (IOException e) {
            System.out.println("Exception during reading file: " + CAR_FILE_NAME + " Cause:  " + e.getCause());
        }
        return carLines;
    }

    private Car parseCar(String carLine, List<CarDetails> carDetails, List<CarPrice> carPrice) {
        String[] carLineSplit = carLine.split(",");

        int carPriceId = Integer.parseInt(carLineSplit[2]);
        int carDetailsId = Integer.parseInt(carLineSplit[1]);

        CarDetails foundCarDetail = findCarDetailsById(carDetails, carDetailsId);
        CarPrice foundCarPrice = findCarPriceById(carPrice, carPriceId);

        return Car.builder()
                .id(Integer.parseInt(carLineSplit[0]))
                .carDetails(foundCarDetail)
                .carPrice(foundCarPrice)
                .build();
    }

    private CarDetails findCarDetailsById(List<CarDetails> carDetails, int id) {
        for (CarDetails cd : carDetails) {
            if (cd.getId() == id) {
                return cd;
            }
        }
        return null;
    }

    private CarPrice findCarPriceById(List<CarPrice> carPrice, int id) {
        for (CarPrice cp : carPrice) {
            if (cp.getId() == id) {
                return cp;
            }
        }
        return null;
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
    public Car update(Car oldCar, Car newCar) {
        return newCar;
    }

    @Override
    public boolean delete(Car car) {
        return true;

    }
}
