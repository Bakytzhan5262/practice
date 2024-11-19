import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        Employee emp1 = new Employee("Uali", "Manager", 5000);
        Employee emp2 = new Employee("Kuro", "Developer", 3000);
        Employee emp3 = new Employee("Baha", "Designer", 3500);
        Employee emp4 = new Employee("Biba", "FS Dev", 2800);

        Department hrDept = new Department("Human Resources", "Head of HR");
        Department techDept = new Department("Technology", "Head of Technology");

        Department softwareDept = new Department("Software", "Head of Software");
        Department qaDept = new Department("Quality Assurance", "Head of QA");

        hrDept.add(emp1);
        techDept.add(emp2);
        techDept.add(emp3);
        techDept.add(emp4);

        softwareDept.add(emp2);
        qaDept.add(emp4);

        techDept.add(softwareDept);
        techDept.add(qaDept);

        System.out.println("Organization Structure:");
        hrDept.display(1);
        techDept.display(1);

        System.out.println("Tech Department Budget: " + techDept.getBudget());
        System.out.println("Tech Department Employee Count: " + techDept.getEmployeeCount());

        emp2.setSalary(4000);
        System.out.println("Tech Department Budget After Salary Change: " + techDept.getBudget());

        OrganizationComponent foundEmployee = techDept.findEmployeeByName("Kuro");
        System.out.println(foundEmployee != null ? foundEmployee.getName() : "Employee not found");

        System.out.println("All Employees in Tech Department:");
        techDept.listAllEmployees();
    }
}

abstract class OrganizationComponent {
    protected String name;
    protected String position;

    public OrganizationComponent(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public abstract void display(int depth);

    public abstract double getBudget();

    public abstract int getEmployeeCount();
}

class Employee extends OrganizationComponent {
    private double salary;

    public Employee(String name, String position, double salary) {
        super(name, position);
        this.salary = salary;
    }

    @Override
    public void display(int depth) {
        System.out.println("-".repeat(depth) + " Employee: " + name + " [Position: " + position + ", Salary: " + salary + "]");
    }

    @Override
    public double getBudget() {
        return salary;
    }

    @Override
    public int getEmployeeCount() {
        return 1;
    }

    public void setSalary(double newSalary) {
        this.salary = newSalary;
    }
}

class Department extends OrganizationComponent {
    private List<OrganizationComponent> subComponents = new ArrayList<>();

    public Department(String name, String position) {
        super(name, position);
    }

    public void add(OrganizationComponent component) {
        subComponents.add(component);
    }

    public void remove(OrganizationComponent component) {
        subComponents.remove(component);
    }

    @Override
    public void display(int depth) {
        System.out.println("-".repeat(depth) + " Department: " + name + " [Position: " + position + "]");
        for (OrganizationComponent component : subComponents) {
            component.display(depth + 2);
        }
    }

    @Override
    public double getBudget() {
        return subComponents.stream().mapToDouble(OrganizationComponent::getBudget).sum();
    }

    @Override
    public int getEmployeeCount() {
        return subComponents.stream().mapToInt(OrganizationComponent::getEmployeeCount).sum();
    }

    public OrganizationComponent findEmployeeByName(String name) {
        for (OrganizationComponent component : subComponents) {
            if (component.getName().equalsIgnoreCase(name)) {
                return component;
            }
            if (component instanceof Department) {
                OrganizationComponent result = ((Department) component).findEmployeeByName(name);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public void listAllEmployees(int depth) {
        for (OrganizationComponent component : subComponents) {
            if (component instanceof Employee) {
                component.display(depth);
            } else if (component instanceof Department) {
                ((Department) component).listAllEmployees(depth + 2);
            }
        }
    }

    public void listAllEmployees() {
        listAllEmployees(1);
    }
}





public class Program {
    public static void main(String[] args) {
        RoomBookingSystem roomBookingSystem = new RoomBookingSystem();
        RestaurantSystem restaurantSystem = new RestaurantSystem();
        EventManagementSystem eventManagementSystem = new EventManagementSystem();
        CleaningService cleaningService = new CleaningService(roomBookingSystem);

        HotelFacade hotelFacade = new HotelFacade(roomBookingSystem, restaurantSystem, eventManagementSystem, cleaningService);

        hotelFacade.settleDown();
        System.out.println();
        hotelFacade.organizeEvent();
        System.out.println();
        hotelFacade.bookRestaurantTable();
        System.out.println();
        hotelFacade.cancelReservation();
        System.out.println();
    }
}

class RoomBookingSystem {
    public void makeRoomReservation(int roomNum) {
        System.out.println("Made a reservation to room: " + roomNum);
    }

    public void isRoomAvailable(boolean available, int roomNum) {
        if (available) {
            System.out.println("The room: " + roomNum + " is available");
        } else {
            System.out.println("The room: " + roomNum + " is not available");
        }
    }

    public void cancelRoomReservation(boolean hasReservation, int roomNum) {
        if (hasReservation) {
            System.out.println("Cancelling the reservation for room: " + roomNum);
        } else {
            System.out.println("You don't have any reservations");
        }
    }
}

class RestaurantSystem {
    public void makeTableReservation(int tableNum) {
        System.out.println("Made a reservation to a table: " + tableNum);
    }

    public void orderFood(String foodName) {
        System.out.println("Ordering: " + foodName);
    }
}

class EventManagementSystem {
    public void orderConferenceRoom(int conferenceRoomNum) {
        System.out.println("Ordering the conference room: " + conferenceRoomNum);
    }

    public void orderEquipment(String equipmentName) {
        System.out.println("Ordering equipment: " + equipmentName);
    }
}

class CleaningService {
    private final RoomBookingSystem roomBookingSystem;

    public CleaningService(RoomBookingSystem roomBookingSystem) {
        this.roomBookingSystem = roomBookingSystem;
    }

    public void setCleaningTime(String cleaningTime, int roomNum) {
        System.out.println("Setting the cleaning time for the room: " + roomNum + ", at " + cleaningTime);
    }

    public void cleaningRoom(boolean roomIsNotClean, String cleaningTime, int roomNum) {
        if (roomIsNotClean) {
            System.out.println("Starting to clean the room: " + roomNum + ", at " + cleaningTime);
        } else {
            System.out.println("The room: " + roomNum + " already has been cleaned at " + cleaningTime);
        }
    }
}

class HotelFacade {
    private final RoomBookingSystem roomBookingSystem;
    private final RestaurantSystem restaurantSystem;
    private final EventManagementSystem eventManagementSystem;
    private final CleaningService cleaningService;

    public HotelFacade(RoomBookingSystem roomBookingSystem, RestaurantSystem restaurantSystem,
                       EventManagementSystem eventManagementSystem, CleaningService cleaningService) {
        this.roomBookingSystem = roomBookingSystem;
        this.restaurantSystem = restaurantSystem;
        this.eventManagementSystem = eventManagementSystem;
        this.cleaningService = cleaningService;
    }

    public void settleDown() {
        System.out.println("Settling down in the hotel");
        roomBookingSystem.isRoomAvailable(true, 404);
        roomBookingSystem.makeRoomReservation(404);
        restaurantSystem.orderFood("Ribeye steak");
        cleaningService.setCleaningTime("17:50", 404);
        cleaningService.cleaningRoom(true, "17:50", 404);
    }

    public void organizeEvent() {
        System.out.println("Organizing an event");
        eventManagementSystem.orderConferenceRoom(267);
        eventManagementSystem.orderEquipment("Projector");
        eventManagementSystem.orderEquipment("Microphone");
        eventManagementSystem.orderEquipment("Surround sound audio system");
    }

    public void bookRestaurantTable() {
        System.out.println("Reserving table in restaurant");
        restaurantSystem.makeTableReservation(4);
        restaurantSystem.orderFood("Shrimp & sausage gumbo");
        restaurantSystem.orderFood("Triple cheese & bacon dauphinoise");
    }

    public void cancelReservation() {
        System.out.println("Cancelling the reservation");
        roomBookingSystem.cancelRoomReservation(true, 404);
        roomBookingSystem.cancelRoomReservation(false, 404);
    }
}
