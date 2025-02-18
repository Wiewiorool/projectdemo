package pl.wasinskipatryk.demo.cardetailsrepository;

import pl.wasinskipatryk.demo.car.Car;
import pl.wasinskipatryk.demo.car.CarDetails;
import pl.wasinskipatryk.demo.car.TypeOfCar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CarDetailsFileBasedRepository implements CarDetailsRepository {

    public static final String CAR_DETAILS_CSV_FILE_NAME = "car_details.csv";

    @Override
    public int addCarDetails(CarDetails carDetailsOfTheCar) {
        CarDetails foundOrNotCarDetails = findCarDetails(carDetailsOfTheCar);
        if (foundOrNotCarDetails == null) {
            System.out.println("Nie znaleźliśmy wybranego CarDetails, dodajemy nowe CarDetails");
            int newCarDetailsId = findMaxCurrentCarDetailsId() + 1;
            String carDetailsLine = carDetailsAsString(newCarDetailsId, carDetailsOfTheCar);
            saveNewCarDetails(carDetailsLine, newCarDetailsId);
            return newCarDetailsId;
        } else {
            System.out.println("Znaleźliśmy takie CarDetails: " + foundOrNotCarDetails.getId());
            return foundOrNotCarDetails.getId();
        }
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

    public CarDetails findCarDetails(CarDetails toBeFound) {
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

    private boolean isCarDetailsTheSameMinusId(CarDetails carDetails1, CarDetails carDetails2) {
        if (carDetails1.getModelName().equals(carDetails2.getModelName())
                && carDetails1.getProductionYear() == carDetails2.getProductionYear()
                && carDetails1.getColor().equals(carDetails2.getColor())
                && carDetails1.getNumberOfDoors() == carDetails2.getNumberOfDoors()
                && carDetails1.getHorsePower() == carDetails2.getHorsePower()
                && carDetails1.getTypeOfCar().equals(carDetails2.getTypeOfCar())) {
            return true;
        }
        return false;
    }

    public List<CarDetails> findAllCarDetails() {
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
}
