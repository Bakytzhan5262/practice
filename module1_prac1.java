package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Car car1 = new Car("BMW","m3",2017,4,"auto");
        System.out.println(car1);
        Motorcyle motorcyle1 = new Motorcyle("Samurai","m8",2020,"sport",true);
        System.out.println(motorcyle1);
        System.out.println(motorcyle1.EngineStart());

        System.out.println("123");

        Garage garage1 = new Garage();
        System.out.println(garage1.addVehicle(car1));
        garage1.addVehicle(motorcyle1);
        System.out.println(garage1);
        System.out.println(car1);


        garage1.deleteVehicle(1);

        Garage garage2 = new Garage();
        garage2.addVehicle(motorcyle1);

        Fleet fleet1 = new Fleet();
        fleet1.addGarage(garage1);
        fleet1.addGarage(garage2);

        fleet1.deleteGarage(1);



    }
}
package com.company;

public class Vehicle extends ShowInfo{
   private String mark;
   private String model;
   private int year;

 public Vehicle(String mark,String model,int year){
     this.mark=mark;
     this.model=model;
     this.year=year;
 }

 public Vehicle(){}

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMark() {
        return mark;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String EngineStart(){
     return showMessage("==Старт двигателя==");
    }
    public String EngineEnd(){
        return showMessage("Стоп двигателя");
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }
}
package com.company;

public class Car extends Vehicle{
    private int numbDoors;
    private String typeTransmission;

    public Car(String mark,String model,int year,int numbDoors,String typeTransmission){
        super(mark,model,year);
        this.numbDoors=numbDoors;
        this.typeTransmission=typeTransmission;
    }
    public Car(){}

    public void setNumbDoors(int numbDoors) {
        this.numbDoors = numbDoors;
    }

    public void setTypeTransmission(String typeTransmission) {
        this.typeTransmission = typeTransmission;
    }

    public int getNumbDoors() {
        return numbDoors;
    }

    public String getTypeTransmission() {
        return typeTransmission;
    }

    @Override
    public String toString() {
        return "Car:" +'\n' +
                "Mark: "+getMark()+'\n' +
                "Model: "+getModel()+'\n' +
                "Year: "+getYear()+'\n' +
                "numbDoors: " + numbDoors +'\n' +
                "typeTransmission: " + typeTransmission+ '\n'+
                "--------------------------------------------";
    }
}
package com.company;

public class Motorcyle extends Vehicle {
   private String typeBody;
   private boolean availabilityofboxing;

   public Motorcyle(String mark,String model,int year,String typeBody,boolean availabilityofboxing){
       super(mark,model,year);
       this.typeBody=typeBody;
       this.availabilityofboxing=availabilityofboxing;
   }
   public Motorcyle(){}

    public void setAvailabilityofboxing(boolean availabilityofboxing) {
        this.availabilityofboxing = availabilityofboxing;
    }

    public void setTypeBody(String typeBody) {
        this.typeBody = typeBody;
    }

    public String getTypeBody() {
        return typeBody;
    }

    public boolean isAvailabilityofboxing() {
        return availabilityofboxing;
    }

    @Override
    public String toString() {
        return "Motorcyle:" +'\n' +
                "Mark: "+getMark()+'\n' +
                "Model: "+getModel()+'\n' +
                "Year: "+getYear()+'\n' +
                "typeBody: " + typeBody +'\n'+
                "availabilityofboxing: " + availabilityofboxing +'\n'+
                "----------------------------------------";
    }
}
package com.company;
import java.util.ArrayList;
public class Garage extends ShowInfo {
    private ArrayList<Vehicle> vehicles;

    public Garage() {
        vehicles = new ArrayList<>();
    }

    public String addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        return showMessage("============транспорт добавлен в гараж=========");
    }

    public String deleteVehicle(int index) {
        if (index >= 0 && index < vehicles.size()) {
            vehicles.remove(index);
            return showMessage("=========транспорт удален из гаража========");
        } else {
            return showMessage("=========Нет такого транспорта в гараже============");
        }
    }

    @Override
    public String toString() {
        return "Garage{" +
                "vehicles=" + vehicles +
                '}';
    }
}
package com.company;

import java.util.ArrayList;

public class Fleet extends ShowInfo {
    private ArrayList<Garage>garages;

    public Fleet(){
        garages = new ArrayList<>();
    }
    public String addGarage(Garage garage){
        garages.add(garage);
        return showMessage("=======Гараж добавлен=========");
    }
    public String deleteGarage(int index) {
        if (index >= 0 && index < garages.size()) {
            garages.remove(index);
            return showMessage("===========Гараж удален=========");
        } else {
            return showMessage("=======ERROR==========");
        }
    }

}
package com.company;

public class ShowInfo {

    public String showMessage(String str) {
      return str;
    }
}
