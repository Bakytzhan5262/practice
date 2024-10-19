package com.company;

public class Main {

    public static void main(String[] args) {
        Order order = new Order();
        order.addItem(new Product("Product 1", 100.0));
        order.addItem(new Product("Product 2", 50.0));

        order.setPaymentMethod(new CreditCardPayment());
        order.setDeliveryMethod(new CourierDelivery());

        IDiscountStrategy discountStrategy = new PercentageDiscount(10);
        order.setDiscountStrategy(discountStrategy);

        order.processOrder();

        Notification notification = new EmailNotification();
        notification.sendNotification("Ваш заказ обработан!");
    }
}
package com.company;

public class Product {
    private String name; // Название продукта
    private double price; // Цена продукта

    
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    
    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
package com.company;

interface Payment {
    void processPayment(double amount);
}
class CreditCardPayment implements Payment{
    public void processPayment(double amount){
        System.out.println("Обработка платежей по кредитной карте "+ amount);
    }
}
class PayPalPayment implements Payment {
    public void processPayment(double amount) {
        System.out.println("Обработка платежа PayPal " + amount);
    }
}

class BankTransferPayment implements Payment {
    public void processPayment(double amount) {
        System.out.println("Обработка банковского перевода платежа " + amount);
    }
}package com.company;

public interface Delivery {
    void deliverOrder(Order order);
}
class CourierDelivery implements Delivery {
    public void deliverOrder(Order order) {
        System.out.println("Доставка заказа курьером.");
    }
}

class PostDelivery implements Delivery {
    public void deliverOrder(Order order) {
        System.out.println("Доставка заказа по почте.");
    }
}

class PickUpPointDelivery implements Delivery {
    public void deliverOrder(Order order) {
        System.out.println("Заказ готов к выдаче.");
    }
}
package com.company;

interface Notification {
    void sendNotification(String message);
}

class EmailNotification implements Notification {
    public void sendNotification(String message) {
        System.out.println("Отправка уведомления по электронной почте: " + message);
    }
}

class SmsNotification implements Notification {
    public void sendNotification(String message) {
        System.out.println("Отправка СМС-уведомления: " + message);
    }
}
package com.company;

class DiscountCalculator {
    private IDiscountStrategy discountStrategy;

    public DiscountCalculator(IDiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double applyDiscount(double total) {
        return discountStrategy.applyDiscount(total);
    }
}
package com.company;

interface IDiscountStrategy {
    double applyDiscount(double total);
}

class FixedDiscount implements IDiscountStrategy {
    private double discountAmount;

    public FixedDiscount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public double applyDiscount(double total) {
        return total - discountAmount;
    }
}

class PercentageDiscount implements IDiscountStrategy {
    private double percentage;

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double total) {
        return total - (total * percentage / 100);
    }
}

class CombinedDiscount implements IDiscountStrategy {
    private IDiscountStrategy[] discounts;

    public CombinedDiscount(IDiscountStrategy... discounts) {
        this.discounts = discounts;
    }

    @Override
    public double applyDiscount(double total) {
        for (IDiscountStrategy discount : discounts) {
            total = discount.applyDiscount(total);
        }
        return total;
    }
}
package com.company;

import java.util.ArrayList;
import java.util.List;

class Order {
    private List<Product> products = new ArrayList<>();
    private Payment paymentMethod;
    private Delivery deliveryMethod;
    private IDiscountStrategy discountStrategy;


    public void addItem(Product item) {
        products.add(item);
    }

    public double calculateTotal() {
        double total = products.stream().mapToDouble(Product::getPrice).sum();
        return total;
    }

    public void setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setDeliveryMethod(Delivery deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setDiscountStrategy(IDiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }


    public void processOrder() {
        DiscountCalculator discountCalculator = new DiscountCalculator(discountStrategy);
        double totalBeforeDiscount = calculateTotal(); // Рассчитываем общую стоимость
        double totalAfterDiscount = discountCalculator.applyDiscount(totalBeforeDiscount); // Применяем скидку


        paymentMethod.processPayment(totalAfterDiscount);
        deliveryMethod.deliverOrder(this);
    }
}
