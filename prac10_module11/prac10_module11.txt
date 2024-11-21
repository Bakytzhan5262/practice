package t2;

public class Room {
    private String roomClass;
    private double price;
    private boolean isAvailable;

    public Room(String roomClass, double price) {
        this.roomClass = roomClass;
        this.price = price;
        this.isAvailable = true;
    }

    public String getRoomClass() {
        return roomClass;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomClass='" + roomClass + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
package t2;

public class User {
    private String name;
    private String email;
    private String password;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
package t2;

import java.util.List;

public class Hotel {
    private String name;
    private String location;
    private List<Room> rooms;

    public Hotel(String name, String location, List<Room> rooms) {
        this.name = name;
        this.location = location;
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
package t2;

import java.time.LocalDate;

public class Booking {
    private User user;
    private Hotel hotel;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;

    public Booking(User user, Hotel hotel, Room room, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.hotel = hotel;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "user=" + user +
                ", hotel=" + hotel.getName() +
                ", room=" + room.getRoomClass() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
package t2;
import java.util.ArrayList;
import java.util.List;


public interface HotelService {
    List<Hotel> searchHotels(String location, String roomClass, double maxPrice);
    void addHotel(Hotel hotel);
}
package t2;

import java.util.ArrayList;
import java.util.List;

public class HotelServiceImpl implements HotelService {
    private List<Hotel> hotels = new ArrayList<>();

    @Override
    public List<Hotel> searchHotels(String location, String roomClass, double maxPrice) {
        List<Hotel> result = new ArrayList<>();
        for (Hotel hotel : hotels) {
            if (hotel.getLocation().equalsIgnoreCase(location)) {
                for (Room room : hotel.getRooms()) {
                    if (room.getRoomClass().equalsIgnoreCase(roomClass) && room.getPrice() <= maxPrice && room.isAvailable()) {
                        result.add(hotel);
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }
}
package t2;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public interface BookingService {
    Booking createBooking(User user, Hotel hotel, Room room, LocalDate startDate, LocalDate endDate);
    boolean checkRoomAvailability(Hotel hotel, Room room, LocalDate startDate, LocalDate endDate);
    List<Booking> getUserBookings(User user);
}
package t2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingServiceImpl implements BookingService {
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public Booking createBooking(User user, Hotel hotel, Room room, LocalDate startDate, LocalDate endDate) {
        if (!checkRoomAvailability(hotel, room, startDate, endDate)) {
            System.out.println("Room is not available for the selected dates.");
            return null;
        }
        room.setAvailable(false);
        Booking booking = new Booking(user, hotel, room, startDate, endDate);
        bookings.add(booking);
        return booking;
    }

    @Override
    public boolean checkRoomAvailability(Hotel hotel, Room room, LocalDate startDate, LocalDate endDate) {
        for (Booking booking : bookings) {
            if (booking.getHotel().equals(hotel) && booking.getRoom().equals(room) &&
                    booking.getStartDate().isBefore(endDate) && booking.getEndDate().isAfter(startDate)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Booking> getUserBookings(User user) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUser().equals(user)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }
}
package t2;

public interface PaymentService {
    boolean processPayment(User user, Booking booking, double amount);
    boolean checkPaymentStatus(Booking booking);
}
package t2;

import java.util.HashMap;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {
    private final Map<Booking, Boolean> paymentRecords = new HashMap<>();

    @Override
    public boolean processPayment(User user, Booking booking, double amount) {
        System.out.println("Обработка платежа от " + user.getName() + " на сумму " + amount );
        paymentRecords.put(booking, true);
        System.out.println("Платеж успешно завершен.");
        return true;
    }

    @Override
    public boolean checkPaymentStatus(Booking booking) {
        return paymentRecords.getOrDefault(booking, false);
    }
}
package t2;

import java.util.List;

public interface UserManagementService {
    User register(String name, String email, String password);
    User login(String email, String password);
    List<Booking> getUserHistory(User user);
}
package t2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManagementServiceImpl implements UserManagementService {
    private final Map<String, User> users = new HashMap<>();
    private final Map<User, List<Booking>> userBookings = new HashMap<>();

    @Override
    public User register(String name, String email, String password) {
        if (users.containsKey(email)) {
            System.out.println("Пользователь с email " + email + " уже существует.");
            return null;
        }
        User user = new User(name, email);
        user.setPassword(password);
        users.put(email, user);
        userBookings.put(user, new ArrayList<>());
        System.out.println("Пользователь " + name + " успешно зарегистрирован.");
        return user;
    }


    @Override
    public User login(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Добро пожаловать, " + user.getName() + "!");
            return user;
        }
        System.out.println("Неправильный email или пароль.");
        return null;
    }

    @Override
    public List<Booking> getUserHistory(User user) {
        return userBookings.getOrDefault(user, new ArrayList<>());
    }

    public void addBooking(User user, Booking booking) {
        userBookings.get(user).add(booking);
    }
}
package t2;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HotelService hotelService = new HotelServiceImpl();
        BookingService bookingService = new BookingServiceImpl();
        PaymentService paymentService = new PaymentServiceImpl();
        NotificationService notificationService = new NotificationServiceImpl();
        UserManagementService userManagementService = new UserManagementServiceImpl();

        User user = userManagementService.register("Губка Боб", "bikiniBottom@gmail.com", "12345678");

        User loggedInUser = userManagementService.login("bikiniBottom@gmail.com", "12345678");
        if (loggedInUser == null) {
            System.out.println("Неверный email или пароль. Прекращение работы программы.");
            return;
        }

        Room room1 = new Room("Стандарт", 100.0);
        Room room2 = new Room("Люкс", 200.0);
        Hotel hotel = new Hotel("Гранд Отель", "Bikini Bottom", List.of(room1, room2));
        hotelService.addHotel(hotel);

        List<Hotel> foundHotels = hotelService.searchHotels("Bikini Bottom", "Люкс", 250.0);
        if (!foundHotels.isEmpty()) {
            System.out.println("Найден отель: " + foundHotels.get(0).getName());
        } else {
            System.out.println("Отель не найден.");
            return;
        }

        Booking booking = bookingService.createBooking(loggedInUser, hotel, room2, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 10));
        if (booking != null) {
            System.out.println("Бронирование создано: " + booking);
        } else {
            System.out.println("Не удалось создать бронирование.");
            return;
        }

        boolean paymentStatus = paymentService.processPayment(loggedInUser, booking, room2.getPrice());
        if (paymentStatus) {
            System.out.println("Платеж успешно обработан.");
        } else {
            System.out.println("Ошибка при обработке платежа.");
            return;
        }

        notificationService.sendBookingConfirmation(loggedInUser, booking);
        notificationService.sendPaymentConfirmation(loggedInUser, booking);

        List<Booking> userBookings = userManagementService.getUserHistory(loggedInUser);
        System.out.println("История бронирований:");
        for (Booking b : userBookings) {
            System.out.println(b);
        }
    }
}

