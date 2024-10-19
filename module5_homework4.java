package com.company;

public interface IVehicle {
    void drive();
    void refuel();
}

package com.company;

public class Car implements IVehicle{
    private String brand;
    private String model;
    private String fuelType;
    public Car(String brand, String model, String fuelType) {
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
    }
    public Car(){};
    @Override
    public void drive() {
        System.out.println("Машина едет");
    }
    public void refuel(){
        System.out.println("Машина заправилась");
    }
}


package com.company;

public class Motorcycle implements IVehicle{
    private String type;
    private int engineVolume;
    private String fuelType;

    public Motorcycle(String type, int engineVolume, String fuelType) {
        this.type = type;
        this.engineVolume = engineVolume;
        this.fuelType = fuelType;
    }
    public Motorcycle(){};
    @Override
    public void drive() {
        System.out.println("Мотоцикл едет");
    }
    public void refuel(){
        System.out.println("Мотоцикл заправился");
    }
}


package com.company;

public class Truck implements IVehicle{
    private int loadCapacity;
    private int axles;
    private String fuelType;

    public Truck(int loadCapacity, int axles, String fuelType) {
        this.loadCapacity = loadCapacity;
        this.axles = axles;
        this.fuelType = fuelType;
    }

    @Override
    public void drive() {
        System.out.println("Грузовик с грузоподъемностью " + loadCapacity + " тонн и количеством осей " + axles + " едет.");
    }

    @Override
    public void refuel() {
        System.out.println("Грузовик заправляется " + fuelType + ".");
    }
}


package com.company;

public class ElectricScooter implements IVehicle {
    private String model;
    private int batteryCapacity;

    public ElectricScooter(String model, int batteryCapacity) {
        this.model = model;
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public void drive() {
        System.out.println("Электросамокат модели " + model + " едет.");
    }

    @Override
    public void refuel() {
        System.out.println("Зарядка электросамоката с ёмкостью батареи " + batteryCapacity + " мАч.");
    }
}


package com.company;

public abstract class VehicleFactory {
    public abstract IVehicle CreateVehicle();
}

package com.company;

public class CarFactory extends VehicleFactory {
    private String brand;
    private String model;
    private String fuelType;

    public CarFactory(String brand, String model, String fuelType) {
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
    }

    @Override
    public IVehicle CreateVehicle() {
        return new Car(brand, model, fuelType);
    }
}

package com.company;

public class ElectricScooterFactory extends VehicleFactory {
    private String model;
    private int batteryCapacity;

    public ElectricScooterFactory(String model, int batteryCapacity) {
        this.model = model;
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public IVehicle CreateVehicle() {
        return new ElectricScooter(model, batteryCapacity);
    }
}


package com.company;

public class MotorcycleFactory extends VehicleFactory {
    private String type;
    private int engineVolume;
    private String fuelType;

    public MotorcycleFactory(String type, int engineVolume, String fuelType) {
        this.type = type;
        this.engineVolume = engineVolume;
        this.fuelType = fuelType;
    }

    @Override
    public IVehicle CreateVehicle() {
        return new Motorcycle(type, engineVolume, fuelType); 
    }
}


package com.company;

public class TruckFactory extends VehicleFactory {
    private int loadCapacity;
    private int axles;
    private String fuelType;

    public TruckFactory(int loadCapacity, int axles, String fuelType) {
        this.loadCapacity = loadCapacity;
        this.axles = axles;
        this.fuelType = fuelType;
    }

    @Override
    public IVehicle CreateVehicle() {
        return new Truck(loadCapacity, axles, fuelType); 
    }
}


package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите тип транспорта: 1) Автомобиль 2) Мотоцикл 3) Грузовик 4) Велосипед 5) Электросамокат");
        int choice = scanner.nextInt();
        scanner.nextLine();

        IVehicle vehicle = null;
        VehicleFactory factory = null;

        switch (choice) {
            case 1:
                System.out.println("Введите марку автомобиля:");
                String carBrand = scanner.nextLine();

                System.out.println("Введите модель автомобиля:");
                String carModel = scanner.nextLine();

                System.out.println("Введите тип топлива:");
                String carFuelType = scanner.nextLine();

                factory = new CarFactory(carBrand, carModel, carFuelType);
                vehicle = factory.CreateVehicle();
                break;

            case 2:
                System.out.println("Введите тип мотоцикла (спортивный, туристический):");
                String motoType = scanner.nextLine();

                System.out.println("Введите объем двигателя:");
                int engineVolume = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Введите тип топлива для мотоцикла:");
                String motoFuelType = scanner.nextLine();

                factory = new MotorcycleFactory(motoType, engineVolume, motoFuelType);
                vehicle = factory.CreateVehicle();
                break;

            case 3:
                System.out.println("Введите грузоподъемность грузовика (в тоннах):");
                int capacity = scanner.nextInt();

                System.out.println("Введите количество осей:");
                int axles = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Введите тип топлива для грузовика:");
                String truckFuelType = scanner.nextLine();

                factory = new TruckFactory(capacity, axles, truckFuelType);
                vehicle = factory.CreateVehicle();
                break;



            case 4:
                System.out.println("Введите модель электросамоката:");
                String scooterModel = scanner.nextLine();

                System.out.println("Введите емкость батареи (в мАч):");
                int batteryCapacity = scanner.nextInt();

                factory = new ElectricScooterFactory(scooterModel, batteryCapacity);
                vehicle = factory.CreateVehicle();
                break;

            default:
                System.out.println("Неверный выбор.");
                break;
        }

        if (vehicle != null) {
            vehicle.drive();
            vehicle.refuel();
        }
    }
}

