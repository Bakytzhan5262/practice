import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager {
    private static ConfigurationManager instance;
    private static final Object lock = new Object();
    private Map<String, String> settings;
    private ConfigurationManager() {
        settings = new HashMap<>();
    })
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    public void loadSettings(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    settings.put(parts[0], parts[1]);
                }
            }
        }
    }

    public void saveSettings(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : settings.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        }
    }
    public String getSetting(String key) {
        if (!settings.containsKey(key)) {
            throw new IllegalArgumentException("Setting not found: " + key);
        }
        return settings.get(key);
    }

    public void setSetting(String key, String value) {
        settings.put(key, value);
    }

    public void removeSetting(String key) {
        settings.remove(key);
    }

    public boolean containsKey(String key) {
        return settings.containsKey(key);
    }
}

public class Main {
    public static void main(String[] args) {
        try {

            ConfigurationManager config = ConfigurationManager.getInstance();
            config.loadSettings("config.txt");

            System.out.println("Host: " + config.getSetting("host"));
            System.out.println("Port: " + config.getSetting("port"));

            config.setSetting("username", "new_user");
            config.saveSettings("new_config.txt");

            Runnable task = () -> {
                ConfigurationManager configInstance = ConfigurationManager.getInstance();
                System.out.println(Thread.currentThread().getName() + ": Host = " + configInstance.getSetting("host"));
            };

            Thread thread1 = new Thread(task);
            Thread thread2 = new Thread(task);
            Thread thread3 = new Thread(task);

            thread1.start();
            thread2.start();
            thread3.start();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}


//ЗАДАЧА-2

public interface IReportBuilder {
    void setHeader(String header);
    void setContent(String content);
    void setFooter(String footer);
    Report getReport();
}
public class Report {
    private String header;
    private String content;
    private String footer;

    public void setHeader(String header) {
        this.header = header;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void display() {
        System.out.println("Header: " + header);
        System.out.println("Content: " + content);
        System.out.println("Footer: " + footer);
    }
}
public class TextReportBuilder implements IReportBuilder {
    private Report report;

    public TextReportBuilder() {
        this.report = new Report();
    }

    @Override
    public void setHeader(String header) {
        report.setHeader("Text Header: " + header);
    }

    @Override
    public void setContent(String content) {
        report.setContent("Text Content: " + content);
    }

    @Override
    public void setFooter(String footer) {
        report.setFooter("Text Footer: " + footer);
    }

    @Override
    public Report getReport() {
        return report;
    }
}
public class HtmlReportBuilder implements IReportBuilder {
    private Report report;

    public HtmlReportBuilder() {
        this.report = new Report();
    }

    @Override
    public void setHeader(String header) {
        report.setHeader("<h1>" + header + "</h1>");
    }

    @Override
    public void setContent(String content) {
        report.setContent("<p>" + content + "</p>");
    }

    @Override
    public void setFooter(String footer) {
        report.setFooter("<footer>" + footer + "</footer>");
    }

    @Override
    public Report getReport() {
        return report;
    }
}
public class ReportDirector {
    public void constructReport(IReportBuilder builder, String header, String content, String footer) {
        builder.setHeader(header);
        builder.setContent(content);
        builder.setFooter(footer);
    }
}
public class Main {
    public static void main(String[] args) {

        ReportDirector director = new ReportDirector();

        IReportBuilder textBuilder = new TextReportBuilder();
        director.constructReport(textBuilder, "Text Report Header", "This is the content of the text report.", "Text Report Footer");
        Report textReport = textBuilder.getReport();
        textReport.display();

        System.out.println("\n-------------------------\n");

        IReportBuilder htmlBuilder = new HtmlReportBuilder();
        director.constructReport(htmlBuilder, "HTML Report Header", "This is the content of the HTML report.", "HTML Report Footer");
        Report htmlReport = htmlBuilder.getReport();
        htmlReport.display();
    }
}
public class XmlReportBuilder implements IReportBuilder {
    private Report report;

    public XmlReportBuilder() {
        this.report = new Report();
    }

    @Override
    public void setHeader(String header) {
        report.setHeader("<header>" + header + "</header>");
    }

    @Override
    public void setContent(String content) {
        report.setContent("<content>" + content + "</content>");
    }

    @Override
    public void setFooter(String footer) {
        report.setFooter("<footer>" + footer + "</footer>");
    }

    @Override
    public Report getReport() {
        return report;
    }
}

public class ReportStyle {
    private String font;
    private String color;
    private int fontSize;

    public ReportStyle(String font, String color, int fontSize) {
        this.font = font;
        this.color = color;
        this.fontSize = fontSize;
    }
  
}

public interface IReportBuilder {
    void setHeader(String header);
    void setContent(String content);
    void setFooter(String footer);
    void setStyle(ReportStyle style);  
    Report getReport();
}
public class Report {
    private String header;
    private String content;
    private String footer;

    public void setHeader(String header) {
        this.header = header;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void updateContent(String newContent) {
        this.content = newContent;  
    }

    public void display() {
        System.out.println("Header: " + header);
        System.out.println("Content: " + content);
        System.out.println("Footer: " + footer);
    }
}

//ЗАДАЧА-3
public interface ICloneable<T> {
    T clone();
}
public class Product implements ICloneable<Product> {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    @Override
    public Product clone() {
        return new Product(this.name, this.price, this.quantity);  
    }

    @Override
    public String toString() {
        return "Product{" +
               "name='" + name + '\'' +
               ", price=" + price +
               ", quantity=" + quantity +
               '}';
    }
}
public class Discount implements ICloneable<Discount> {
    private String description;
    private double discountAmount;

    public Discount(String description, double discountAmount) {
        this.description = description;
        this.discountAmount = discountAmount;
    }



    @Override
    public Discount clone() {
        return new Discount(this.description, this.discountAmount);  
    }

    @Override
    public String toString() {
        return "Discount{" +
               "description='" + description + '\'' +
               ", discountAmount=" + discountAmount +
               '}';
    }
}
import java.util.ArrayList;
import java.util.List;

public class Order implements ICloneable<Order> {
    private List<Product> products = new ArrayList<>();
    private double deliveryCost;
    private Discount discount;
    private String paymentMethod;

    public Order(List<Product> products, double deliveryCost, Discount discount, String paymentMethod) {
        this.products = products;
        this.deliveryCost = deliveryCost;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
    }


    @Override
    public Order clone() {
        List<Product> clonedProducts = new ArrayList<>();
        for (Product product : products) {
            clonedProducts.add(product.clone());  
        }
        return new Order(clonedProducts, this.deliveryCost, 
                         this.discount != null ? this.discount.clone() : null, 
                         this.paymentMethod);
    }

    @Override
    public String toString() {
        return "Order{" +
               "products=" + products +
               ", deliveryCost=" + deliveryCost +
               ", discount=" + discount +
               ", paymentMethod='" + paymentMethod + '\'' +
               '}';
    }
}
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Product product1 = new Product("Laptop", 1000.00, 1);
        Product product2 = new Product("Mouse", 25.00, 2);

        Discount discount = new Discount("New Year Discount", 100.00);

        Order originalOrder = new Order(Arrays.asList(product1, product2), 20.00, discount, "Credit Card");

        Order clonedOrder = originalOrder.clone();

        clonedOrder.getProducts().get(0).setQuantity(2);  
        clonedOrder.setPaymentMethod("PayPal");

        System.out.println("Original Order:");
        System.out.println(originalOrder);

        System.out.println("\nCloned Order:");
        System.out.println(clonedOrder);
    }
}

