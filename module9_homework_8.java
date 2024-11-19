public class Program {
    public static void main(String[] args) {
        IPaymentProcessor paypalProcessor = new PayPalPaymentProcessor();
        paypalProcessor.processPayment(100.0);
        paypalProcessor.refundPayment(50.0);

        StripePaymentService stripeService = new StripePaymentService();
        IPaymentProcessor stripeProcessor = new StripePaymentAdapter(stripeService);
        stripeProcessor.processPayment(200.0);
        stripeProcessor.refundPayment(100.0);

        ExternalPaymentService externalService = new ExternalPaymentService();
        IPaymentProcessor externalProcessor = new ExternalPaymentAdapter(externalService);
        externalProcessor.processPayment(300.0);
        externalProcessor.refundPayment(150.0);
    }
}

interface IPaymentProcessor {
    void processPayment(double amount);
    void refundPayment(double amount);
}

class PayPalPaymentProcessor implements IPaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment " + amount + " via PayPal.\n");
    }

    @Override
    public void refundPayment(double amount) {
        System.out.println("Refunding payment " + amount + " via PayPal.\n");
    }
}

class StripePaymentService {
    public void makeTransaction(double totalAmount) {
        System.out.println("Making transaction " + totalAmount + " via Stripe.\n");
    }

    public void makeRefund(double totalAmount) {
        System.out.println("Refunding transaction " + totalAmount + " via Stripe.\n");
    }
}

class StripePaymentAdapter implements IPaymentProcessor {
    private StripePaymentService stripePaymentService;

    public StripePaymentAdapter(StripePaymentService stripePaymentService) {
        this.stripePaymentService = stripePaymentService;
    }

    @Override
    public void processPayment(double amount) {
        stripePaymentService.makeTransaction(amount);
    }

    @Override
    public void refundPayment(double amount) {
        stripePaymentService.makeRefund(amount);
    }
}

class ExternalPaymentService {
    public void executePayment(double amount) {
        System.out.println("Executing payment " + amount + " via External Payment Service.\n");
    }

    public void executeRefund(double amount) {
        System.out.println("Executing refund " + amount + " via External Payment Service.\n");
    }
}

class ExternalPaymentAdapter implements IPaymentProcessor {
    private ExternalPaymentService externalPaymentService;

    public ExternalPaymentAdapter(ExternalPaymentService externalPaymentService) {
        this.externalPaymentService = externalPaymentService;
    }

    @Override
    public void processPayment(double amount) {
        externalPaymentService.executePayment(amount);
    }

    @Override
    public void refundPayment(double amount) {
        externalPaymentService.executeRefund(amount);
    }
}
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        IBeverage beverage = new Coffee();
        boolean addingIngredients = true;
        Scanner scanner = new Scanner(System.in);

        while (addingIngredients) {
            System.out.println("\nCurrent Order: " + beverage.getDescription());
            System.out.println("Current Cost: " + beverage.getCost());

            System.out.println("\nChoose an ingredient to add:");
            System.out.println("1. Milk (+10.0)");
            System.out.println("2. Sugar (+5.0)");
            System.out.println("3. Whipped Cream (+12.0)");
            System.out.println("4. Done - Finish order");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    beverage = new MilkDecorator(beverage);
                    System.out.println("Added Milk.");
                    break;
                case "2":
                    beverage = new SugarDecorator(beverage);
                    System.out.println("Added Sugar.");
                    break;
                case "3":
                    beverage = new WhippedCreamDecorator(beverage);
                    System.out.println("Added Whipped Cream.");
                    break;
                case "4":
                    addingIngredients = false;
                    break;
                default:
                    System.out.println("Non-existing choice, restart.");
                    break;
            }
        }

        System.out.println("\nFinal Order: " + beverage.getDescription());
        System.out.println("Total Cost: " + beverage.getCost());
        scanner.close();
    }
}

interface IBeverage {
    double getCost();
    String getDescription();
}

class Coffee implements IBeverage {
    @Override
    public double getCost() {
        return 50.0;
    }

    @Override
    public String getDescription() {
        return "Coffee";
    }
}

class Espresso implements IBeverage {
    @Override
    public double getCost() {
        return 60.0;
    }

    @Override
    public String getDescription() {
        return "Espresso";
    }
}

class Tea implements IBeverage {
    @Override
    public double getCost() {
        return 40.0;
    }

    @Override
    public String getDescription() {
        return "Tea";
    }
}

abstract class BeverageDecorator implements IBeverage {
    protected IBeverage beverage;

    public BeverageDecorator(IBeverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double getCost() {
        return beverage.getCost();
    }

    @Override
    public String getDescription() {
        return beverage.getDescription();
    }
}

class MilkDecorator extends BeverageDecorator {
    public MilkDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 10.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Milk";
    }
}

class SugarDecorator extends BeverageDecorator {
    public SugarDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 5.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Sugar";
    }
}

class WhippedCreamDecorator extends BeverageDecorator {
    public WhippedCreamDecorator(IBeverage beverage) {
        super(beverage);
    }

    @Override
    public double getCost() {
        return super.getCost() + 12.0;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Whipped Cream";
    }
}

