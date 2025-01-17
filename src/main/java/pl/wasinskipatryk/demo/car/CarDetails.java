package pl.wasinskipatryk.demo.car;

import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Builder
@Getter
public class CarDetails {
    private int id;
    private String modelName;
    private int productionYear;
    private String color;
    private int numberOfDoors;
    private int horsePower;
    private TypeOfCar typeOfCar;


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

