package pl.wasinskipatryk.demo;

public class CarDetails {
    private String modelName;
    private int productionYear;
    private String color;
    private int numberOfDoors;
    private int horsePower;
    private TypeOfCar typeOfCar;

    public CarDetails(String modelName, int productionYear, String color, int numberOfDoors, int horsePower, TypeOfCar typeOfCar) {
        this.modelName = modelName;
        this.productionYear = productionYear;
        this.color = color;
        this.numberOfDoors = numberOfDoors;
        this.horsePower = horsePower;
        this.typeOfCar = typeOfCar;
    }
}
