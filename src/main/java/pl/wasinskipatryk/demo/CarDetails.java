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

    public String getModelName() {
        return modelName;
    }

    @Override
    public String toString() {
        return "CarDetails{" +
                "modelName='" + modelName + '\'' +
                ", productionYear=" + productionYear +
                ", color='" + color + '\'' +
                ", numberOfDoors=" + numberOfDoors +
                ", horsePower=" + horsePower +
                ", typeOfCar=" + typeOfCar +
                '}';
    }
}

