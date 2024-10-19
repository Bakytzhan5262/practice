1-задача
package com.company;

public class Order {
    private String productName;
    private int quantity;
    private double price;

    public Order(String productName, int quantity, double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
package com.company;

public class PriceCalculator {
    public double calculateTotalPrice(Order order) {
        return order.getQuantity() * order.getPrice() * 0.9;
    }
}
package com.company;

public class PaymentProcessor {
    public void processPayment(String paymentDetails) {
        System.out.println("Payment processed using: " + paymentDetails);
    }
}
package com.company;

public class EmailNotifier {
    public void sendConfirmationEmail(String email) {
  
        System.out.println("Confirmation email sent to: " + email);
    }
}
public class Main {
    public static void main(String[] args) {
   
        Order order = new Order("Product A", 2, 50.0);
        PriceCalculator priceCalculator = new PriceCalculator();
        double totalPrice = priceCalculator.calculateTotalPrice(order);
        System.out.println("Total Price: " + totalPrice);

        PaymentProcessor paymentProcessor = new PaymentProcessor();
        paymentProcessor.processPayment("Credit Card");

        EmailNotifier emailNotifier = new EmailNotifier();
        emailNotifier.sendConfirmationEmail("customer@example.com");
    }
}

2-задача
package com.company;

public interface ISalaryCalculator {
    double calculateSalary(Employee employee);
}
package com.company;

public class PermanentEmployeeSalaryCalculator implements ISalaryCalculator {
    @Override
    public double calculateSalary(Employee employee) {
        return employee.BaseSalary * 1.2; 
    }
}

public class ContractEmployeeSalaryCalculator implements ISalaryCalculator {
    @Override
    public double calculateSalary(Employee employee) {
        return employee.BaseSalary * 1.1; 
    }
}

public class InternEmployeeSalaryCalculator implements ISalaryCalculator {
    @Override
    public double calculateSalary(Employee employee) {
        return employee.BaseSalary * 0.8; 
    }
}


public class FreelancerEmployeeSalaryCalculator implements ISalaryCalculator {
    @Override
    public double calculateSalary(Employee employee) {
        return employee.BaseSalary * 1.0; 
    }
}

package com.company;

import java.util.HashMap;
import java.util.Map;

public class EmployeeSalaryCalculator {
    private Map<String, ISalaryCalculator> salaryCalculators = new HashMap<>();

    public EmployeeSalaryCalculator() {
       
        salaryCalculators.put("Permanent", new PermanentEmployeeSalaryCalculator());
        salaryCalculators.put("Contract", new ContractEmployeeSalaryCalculator());
        salaryCalculators.put("Intern", new InternEmployeeSalaryCalculator());
        salaryCalculators.put("Freelancer", new FreelancerEmployeeSalaryCalculator());
    }

    public double calculateSalary(Employee employee) {
        ISalaryCalculator calculator = salaryCalculators.get(employee.EmployeeType);
        if (calculator != null) {
            return calculator.calculateSalary(employee);
        } else {
            throw new UnsupportedOperationException("Employee type not supported");
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Employee permanentEmployee = new Employee("Alice", 1000, "Permanent");
        Employee contractEmployee = new Employee("Bob", 1000, "Contract");
        Employee internEmployee = new Employee("Charlie", 500, "Intern");
        Employee freelancerEmployee = new Employee("David", 800, "Freelancer");

        EmployeeSalaryCalculator salaryCalculator = new EmployeeSalaryCalculator();

        System.out.println("Permanent Salary: " + salaryCalculator.calculateSalary(permanentEmployee));
        System.out.println("Contract Salary: " + salaryCalculator.calculateSalary(contractEmployee));
        System.out.println("Intern Salary: " + salaryCalculator.calculateSalary(internEmployee));
        System.out.println("Freelancer Salary: " + salaryCalculator.calculateSalary(freelancerEmployee));
    }
}

3-задача
package com.company;

public interface IPrinter {
    void print(String content);
}

public interface IScanner {
    void scan(String content);
}

public interface IFax {
    void fax(String content);
}
package com.company;

public class AllInOnePrinter implements IPrinter, IScanner, IFax {
    @Override
    public void print(String content) {
        System.out.println("Printing: " + content);
    }

    @Override
    public void scan(String content) {
        System.out.println("Scanning: " + content);
    }

    @Override
    public void fax(String content) {
        System.out.println("Faxing: " + content);
    }
}
package com.company;

public class BasicPrinter implements IPrinter {
    @Override
    public void print(String content) {
        System.out.println("Printing: " + content);
    }
}
package com.company;

public class ScannerPrinter implements IPrinter, IScanner {
    @Override
    public void print(String content) {
        System.out.println("Printing: " + content);
    }

    @Override
    public void scan(String content) {
        System.out.println("Scanning: " + content);
    }
}
public class Main {
    public static void main(String[] args) {
        IPrinter allInOnePrinter = new AllInOnePrinter();
        allInOnePrinter.print("Document 1");
        ((IScanner) allInOnePrinter).scan("Document 1");
        ((IFax) allInOnePrinter).fax("Document 1");

        IPrinter basicPrinter = new BasicPrinter();
        basicPrinter.print("Document 2");

        IPrinter scannerPrinter = new ScannerPrinter();
        scannerPrinter.print("Document 3");
        ((IScanner) scannerPrinter).scan("Document 3");
        
      
    }
}


4-задача
package com.company;

public interface IMessageSender {
    void sendMessage(String message);
}
package com.company;

public class EmailSender implements IMessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("Email sent: " + message);
    }
}

public class SmsSender implements IMessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("SMS sent: " + message);
    }
}


public class MessengerSender implements IMessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("Message sent via messenger: " + message);
    }
}
package com.company;

import java.util.List;

public class NotificationService {
    private List<IMessageSender> messageSenders;

    public NotificationService(List<IMessageSender> messageSenders) {
        this.messageSenders = messageSenders;
    }

    public void sendNotification(String message) {
        for (IMessageSender sender : messageSenders) {
            sender.sendMessage(message);
        }
    }
}

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {     
        List<IMessageSender> senders = Arrays.asList(new EmailSender(), new SmsSender(), new MessengerSender());  
        NotificationService notificationService = new NotificationService(senders);
        notificationService.sendNotification("Hello, this is a notification!");
    }
}
