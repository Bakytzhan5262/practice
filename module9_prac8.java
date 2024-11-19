
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose a delivery service:");
        System.out.println("1. Internal Delivery Service");
        System.out.println("2. External Logistics Service A");
        System.out.println("3. External Logistics Service B");
        System.out.print("Enter your choice (1-3): ");

        String input = scanner.nextLine();
        IInternalDeliveryService deliveryService;

        try {
            switch (input) {
                case "1":
                    deliveryService = DeliveryServiceFactory.createDeliveryService("internal");
                    break;
                case "2":
                    deliveryService = DeliveryServiceFactory.createDeliveryService("externalA");
                    break;
                case "3":
                    deliveryService = DeliveryServiceFactory.createDeliveryService("externalB");
                    break;
                default:
                    System.out.println("Invalid choice, restart.");
                    return;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        String orderId = "404";
        deliveryService.deliverOrder(orderId);
        System.out.println(deliveryService.getDeliveryStatus(orderId));
    }
}


interface IInternalDeliveryService {
    void deliverOrder(String orderId);

    String getDeliveryStatus(String orderId);
}


class InternalDeliveryService implements IInternalDeliveryService {
    @Override
    public void deliverOrder(String orderId) {
        System.out.println("Delivering order " + orderId + " by internal delivery service.");
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        return "Status of order " + orderId + ": Delivered.";
    }
}


class ExternalLogisticsServiceA {
    public void shipItem(int itemId) {
        System.out.println("Shipping item " + itemId + " by External Logistics Service A.");
    }

    public String trackShipment(int shipmentId) {
        return "Tracking shipment " + shipmentId + " by External Logistics Service A: In Transit.";
    }
}


class ExternalLogisticsServiceB {
    public void sendPackage(String packageInfo) {
        System.out.println("Sending package with info '" + packageInfo + "' by External Logistics Service B.");
    }

    public String checkPackageStatus(String trackingCode) {
        return "Status of package with tracking code " + trackingCode + " by External Logistics Service B: Delivered.";
    }
}


class LogisticsAdapterA implements IInternalDeliveryService {
    private final ExternalLogisticsServiceA externalService;

    public LogisticsAdapterA(ExternalLogisticsServiceA externalService) {
        this.externalService = externalService;
    }

    @Override
    public void deliverOrder(String orderId) {
        int itemId = Integer.parseInt(orderId);
        externalService.shipItem(itemId);
        Logger.logAction("Order " + orderId + " delivered by External Logistics Service A.");
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        int shipmentId = Integer.parseInt(orderId);
        String status = externalService.trackShipment(shipmentId);
        Logger.logAction("Checked delivery status for order " + orderId + " by External Logistics Service A.");
        return status;
    }
}


class LogisticsAdapterB implements IInternalDeliveryService {
    private final ExternalLogisticsServiceB externalService;

    public LogisticsAdapterB(ExternalLogisticsServiceB externalService) {
        this.externalService = externalService;
    }

    @Override
    public void deliverOrder(String orderId) {
        externalService.sendPackage(orderId);
        Logger.logAction("Order " + orderId + " delivered by External Logistics Service B.");
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        String status = externalService.checkPackageStatus(orderId);
        Logger.logAction("Checked delivery status for order " + orderId + " by External Logistics Service B.");
        return status;
    }
}


class DeliveryServiceFactory {
    public static IInternalDeliveryService createDeliveryService(String type) {
        switch (type) {
            case "internal":
                return new InternalDeliveryService();
            case "externalA":
                return new LogisticsAdapterA(new ExternalLogisticsServiceA());
            case "externalB":
                return new LogisticsAdapterB(new ExternalLogisticsServiceB());
            default:
                throw new IllegalArgumentException("Non-existing delivery service, restart.");
        }
    }
}

class Logger {
    private static final String LOG_FILE_PATH = "log.txt";

    public static void logAction(String message) {
        String logMessage = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ": " + message;
        System.out.println(logMessage);

        try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
            writer.write(logMessage + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
}



import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        String exportPath = "C:\\Users\\Bekal\\Documents\\my_own_documents\\vs studio lol\\uni\\pattern\\practice\\9\\reports\\report";

        List<Sale> salesData = new ArrayList<>();
        salesData.add(new Sale(LocalDate.now().minusDays(12), 100));
        salesData.add(new Sale(LocalDate.now().minusDays(3), 200));
        salesData.add(new Sale(LocalDate.now().minusDays(5), 150));
        salesData.add(new Sale(LocalDate.now().minusDays(10), 300));

        IReport salesReport = new SalesReport(salesData);
        salesReport = new DateFilterDecorator(salesReport, LocalDate.now().minusDays(10), LocalDate.now());
        salesReport = new CsvExportDecorator(salesReport, exportPath + "_sales.csv");
        salesReport = new PdfExportDecorator(salesReport, exportPath + "_sales.pdf");
        System.out.println("Sales Report:");
        System.out.println(salesReport.generate());

        List<User> userData = new ArrayList<>();
        userData.add(new User(2, "Kuro", LocalDate.now().minusDays(30)));
        userData.add(new User(3, "Uali", LocalDate.now().minusDays(10)));
        userData.add(new User(1, "Baha", LocalDate.now().minusDays(15)));

        IReport userReport = new UserReport(userData);
        userReport = new SortingDecorator(userReport, "UserID");
        userReport = new CsvExportDecorator(userReport, exportPath + "_users.csv");
        userReport = new PdfExportDecorator(userReport, exportPath + "_users.pdf");
        System.out.println("\nUser Report:");
        System.out.println(userReport.generate());
    }
}

interface IReport {
    String generate();
}

class Sale {
    private LocalDate date;
    private int amount;

    public Sale(LocalDate date, int amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }
}

class User {
    private int userID;
    private String name;
    private LocalDate registrationDate;

    public User(int userID, String name, LocalDate registrationDate) {
        this.userID = userID;
        this.name = name;
        this.registrationDate = registrationDate;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
}

class SalesReport implements IReport {
    private List<Sale> salesData;

    public SalesReport(List<Sale> salesData) {
        this.salesData = salesData;
    }

    @Override
    public String generate() {
        StringBuilder report = new StringBuilder("Date, Amount\n");
        for (Sale sale : salesData) {
            report.append(sale.getDate().format(DateTimeFormatter.ISO_DATE))
                    .append(", ")
                    .append(sale.getAmount())
                    .append("\n");
        }
        return report.toString();
    }

    public List<Sale> getFilteredSales(LocalDate startDate, LocalDate endDate) {
        List<Sale> filtered = new ArrayList<>();
        for (Sale sale : salesData) {
            if (!sale.getDate().isBefore(startDate) && !sale.getDate().isAfter(endDate)) {
                filtered.add(sale);
            }
        }
        return filtered;
    }
}

class UserReport implements IReport {
    private List<User> userData;

    public UserReport(List<User> userData) {
        this.userData = userData;
    }

    @Override
    public String generate() {
        StringBuilder report = new StringBuilder("UserID, Name, RegistrationDate\n");
        for (User user : userData) {
            report.append(user.getUserID())
                    .append(", ")
                    .append(user.getName())
                    .append(", ")
                    .append(user.getRegistrationDate().format(DateTimeFormatter.ISO_DATE))
                    .append("\n");
        }
        return report.toString();
    }

    public List<User> getSortedUsers(String sortBy) {
        switch (sortBy) {
            case "UserID":
                userData.sort(Comparator.comparingInt(User::getUserID));
                break;
            case "Name":
                userData.sort(Comparator.comparing(User::getName));
                break;
            case "RegistrationDate":
                userData.sort(Comparator.comparing(User::getRegistrationDate));
                break;
        }
        return userData;
    }
}

abstract class ReportDecorator implements IReport {
    protected IReport report;

    public ReportDecorator(IReport report) {
        this.report = report;
    }

    @Override
    public String generate() {
        return report.generate();
    }
}

class DateFilterDecorator extends ReportDecorator {
    private LocalDate startDate;
    private LocalDate endDate;

    public DateFilterDecorator(IReport report, LocalDate startDate, LocalDate endDate) {
        super(report);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String generate() {
        if (report instanceof SalesReport) {
            SalesReport salesReport = (SalesReport) report;
            List<Sale> filteredSales = salesReport.getFilteredSales(startDate, endDate);
            StringBuilder reportData = new StringBuilder("Date, Amount\n");
            for (Sale sale : filteredSales) {
                reportData.append(sale.getDate().format(DateTimeFormatter.ISO_DATE))
                        .append(", ")
                        .append(sale.getAmount())
                        .append("\n");
            }
            return "Filtered by dates from " + startDate + " to " + endDate + ":\n" + reportData;
        }
        return super.generate();
    }
}

class SortingDecorator extends ReportDecorator {
    private String sortBy;

    public SortingDecorator(IReport report, String sortBy) {
        super(report);
        this.sortBy = sortBy;
    }

    @Override
    public String generate() {
        if (report instanceof UserReport) {
            UserReport userReport = (UserReport) report;
            userReport.getSortedUsers(sortBy);
        }
        return "Sorted by " + sortBy + ":\n" + super.generate();
    }
}

class CsvExportDecorator extends ReportDecorator {
    private String filePath;

    public CsvExportDecorator(IReport report, String filePath) {
        super(report);
        this.filePath = filePath;
    }

    @Override
    public String generate() {
        String data = super.generate();
        try {
            Files.write(Paths.get(filePath), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data + " (Exported to CSV at " + filePath + ")";
    }
}

class PdfExportDecorator extends ReportDecorator {
    private String filePath;

    public PdfExportDecorator(IReport report, String filePath) {
        super(report);
        this.filePath = filePath;
    }

    @Override
    public String generate() {
        String data = super.generate();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph(data));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data + " (Exported to PDF at " + filePath + ")";
    }
}
