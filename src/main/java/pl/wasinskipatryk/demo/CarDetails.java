package pl.wasinskipatryk.demo;

import lombok.Builder;

@Builder
public class CarDetails {
    private String modelName;
    private int productionYear;
    private String color;
    private int numberOfDoors;
    private int horsePower;
    private TypeOfCar typeOfCar;

}

