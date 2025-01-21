package pl.wasinskipatryk.demo.carsrepository;

import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarDetails;
import pl.wasinskipatryk.demo.car.CarPrice;
import pl.wasinskipatryk.demo.car.TypeOfCar;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

        int newIdForCar = findMaxCurrentId() + 1; //  CAR_ID - Potrzebujemy się dowiedzieć jakie id powinno dostac - findMaxId() + 1
        String carToBeAdded1 = newIdForCar + ","; //  Zamienić carToBeAdded na Stringa --> carToBeAdded => 5,4,4

        // 3. CAR_DETAILS_ID = sprawdzamy, czy nie mamy już takiego car_details
        CarDetails carDetailsOfTheCar = carToBeAdded.getCarDetails();
        CarDetails foundOrNotCarDetails = findCarDetails(carDetailsOfTheCar);// może byc nullem

        if (foundOrNotCarDetails == null) { //jak dodawałem != null nie pokazywało się nowe auto z nowmy id?
            System.out.println("Nie znaleźliśmy wybranego CarDetails, dodajemy nowe CarDetails");
            int newCarDetailsId = findMaxCurrentCarDetailsId() + 1;
            String carDetailsLine = carDetailsAsString(newCarDetailsId, carDetailsOfTheCar);
            saveNewCarDetails(carDetailsLine, newCarDetailsId);
            carToBeAdded1 += newCarDetailsId + ",";
        } else {
            carToBeAdded1 += foundOrNotCarDetails.getId() + ",";
            System.out.println("Znaleźliśmy takie CarDetails: " + foundOrNotCarDetails.getId());
            return true;
        }

        //    a. jeśli mamy (nie jest nullem) - to używamy jego i jego id
        //    b. jesli nie (jest nullem) - to tworzymy nowy i używamy jego nowego id

        //HW

        // 4. CAR_PRICE_ID = sprawdzamy, czy nie mamy już takiego car_price
        //    a. jeśli mamy - to używamy jego i jego id
        //    b. jesli nie - to tworzymy nowy i używamy jego nowego id
        // jak mamy te wszystkie dane, to mamy też od razu Stringa (wiersz) do zapisania
        // do pliku car.csv
        // np: 5,4,2
        // nowe auto z id 5, o nowym car_details z id 4 , ale ze starym car_price o id 2
        return true;
    }


    private String carDetailsAsString(int carDetailsId, CarDetails carDetails) {

        String line = carDetailsId + "," + carDetails.getModelName() + "," + carDetails.getProductionYear() + ","
                + carDetails.getColor() + "," + carDetails.getNumberOfDoors() + "," + carDetails.getHorsePower() + ","
                + carDetails.getTypeOfCar() + "\n";

        return line;
    }

    private static void saveNewCarDetails(String carDetailsToBeSaved, int newCarDetailsId) {
        Path carDetailsPath = Paths.get(CAR_DETAILS_CSV_FILE_NAME);
        try {
            Files.writeString(carDetailsPath, carDetailsToBeSaved, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Nie udało się wpisać " + e.getMessage());
        }

        System.out.println("Saved new car details with id: " + newCarDetailsId);
    }

    private int findMaxCurrentCarDetailsId() {
        List<CarDetails> allCarDetails = findAllCarDetails();
        int max = 0;
        for (int i = 0; i < allCarDetails.size(); i++) {
            CarDetails carDetails = allCarDetails.get(i);
            if (carDetails.getId() > max) {
                max = carDetails.getId();
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

    private CarDetails findCarDetails(CarDetails toBeFound) {
        // jeśli takie carDetails już istnieje w pliku to go zwróć
        // jeśi nie istnieje - zwróć null
        List<CarDetails> allCarDetails = findAllCarDetails();
        for (int i = 0; i < allCarDetails.size(); i++) {
            CarDetails carDetails = allCarDetails.get(i);
            if (isCarDetailsTheSameMinusId(carDetails, toBeFound)) {
                return carDetails;
            }
        }
        return null;
    }

    // HW 1
    private boolean isCarDetailsTheSameMinusId(CarDetails carDetails1, CarDetails carDetails2) {
        //zwraca true gdy wszystkie pole, prócz id, są sobie równe
        // jeśli któreś się różni -> false
        if (carDetails1.getModelName().equals(carDetails2.getModelName())
                && carDetails1.getProductionYear() == carDetails2.getProductionYear()
                && carDetails1.getColor().equals(carDetails2.getColor())
                && carDetails1.getNumberOfDoors() == carDetails2.getNumberOfDoors()
                && carDetails1.getHorsePower() == carDetails2.getHorsePower()
                && carDetails1.getTypeOfCar().equals(carDetails2.getTypeOfCar())) {

            return true;
        }
        //hw dokonczyc
        return false;
    }

    private List<CarDetails> findAllCarDetails() {
        List<CarDetails> carDetails = new ArrayList<>();
        Path carDetailsPath = Paths.get(CAR_DETAILS_CSV_FILE_NAME);
        List<String> carDetailsLines = new ArrayList<>();
        try {
            carDetailsLines = Files.readAllLines(carDetailsPath);
        } catch (IOException e) {
            System.out.println("Exception during reading file: " + CAR_DETAILS_CSV_FILE_NAME + " Cause:  " + e.getCause());
        }
        for (int i = 0; i < carDetailsLines.size(); i++) {
            String carDetailAsString = carDetailsLines.get(i); // "1,Audi,2014,black,5,220,KOMBI"
            String[] carDetailsAsArray = carDetailAsString.split(",");
            CarDetails carDetails1 = CarDetails.builder()
                    .id(Integer.parseInt(carDetailsAsArray[0]))
                    .modelName(carDetailsAsArray[1])
                    .productionYear(Integer.parseInt(carDetailsAsArray[2]))
                    .color(carDetailsAsArray[3])
                    .numberOfDoors(Integer.parseInt(carDetailsAsArray[4]))
                    .horsePower(Integer.parseInt(carDetailsAsArray[5]))
                    .typeOfCar(TypeOfCar.valueOf(carDetailsAsArray[6]))
                    .build();
            carDetails.add(carDetails1);
        }
        return carDetails;
    }

    @Override
    public void update(Car oldCar, Car newCar) {

    }

    @Override
    public void delete(Car car) {

    }
}
