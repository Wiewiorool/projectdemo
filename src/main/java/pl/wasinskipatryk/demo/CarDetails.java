package pl.wasinskipatryk.demo;

public class CarDetails {
    private String modelName;
    private int productionYear;
    private String color;
    private int numberOfDoors;
    private int horsePower;
    private TypeOfCar typeOfCar;

    private CarDetails(CarDetailsBuilder carDetailsBuilder) {
        this.modelName = carDetailsBuilder.modelName;
        this.productionYear = carDetailsBuilder.productionYear;
        this.color = carDetailsBuilder.color;
        this.numberOfDoors = carDetailsBuilder.numberOfDoors;
        this.horsePower = carDetailsBuilder.horsePower;
        this.typeOfCar = carDetailsBuilder.typeOfCar;
    }

    public static class CarDetailsBuilder {
        private String modelName;
        private int productionYear;
        private String color;
        private int numberOfDoors;
        private int horsePower;
        private TypeOfCar typeOfCar;

        public CarDetailsBuilder setModelName(String modelName) {
            this.modelName = modelName;
            return this;
        }

        public CarDetailsBuilder setProductionYear(int productionYear) {
            this.productionYear = productionYear;
            return this;
        }

        public CarDetailsBuilder setColor(String color) {
            this.color = color;
            return this;
        }

        public CarDetailsBuilder setNumberOfDoors(int numberOfDoors) {
            this.numberOfDoors = numberOfDoors;
            return this;
        }

        public CarDetailsBuilder setHorsePower(int horsePower) {
            this.horsePower = horsePower;
            return this;
        }

        public CarDetailsBuilder setTypeOfCar(TypeOfCar typeOfCar) {
            this.typeOfCar = typeOfCar;
            return this;
        }

        public CarDetails build(){
            return new CarDetails(this);
        }
    }
}
