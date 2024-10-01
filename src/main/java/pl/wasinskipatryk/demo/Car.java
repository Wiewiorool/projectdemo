package pl.wasinskipatryk.demo;

import java.math.BigDecimal;

public class Car {
    private String brand;
    private int productionYear;
    private String color;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private int numberOfDoors;
    private int horsePower;
    private TypeOfCar typeOfCar;

    public Car(String brand, int productionYear, String color,
               BigDecimal buyPrice, BigDecimal sellPrice,
               int numberOfDoors, int horsePower,
               TypeOfCar typeOfCar) {

        this.brand = brand;
        this.productionYear = productionYear;
        this.color = color;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.numberOfDoors = numberOfDoors;
        this.horsePower = horsePower;
        this.typeOfCar = typeOfCar;
    }
}
