package com;

public interface IPaymentStrategy {
    void pay(double amount);
}
package com;

public class CryptoPaymentStrategy implements IPaymentStrategy {
    private String walletAddress;

    public CryptoPaymentStrategy(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Оплачено " + amount + " криптовалютой через кошелек " + walletAddress);
    }
}
package com;

public class CreditCardPaymentStrategy implements IPaymentStrategy {
    private String cardNumber;
    private String cardHolder;

    public CreditCardPaymentStrategy(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Оплачено " + amount + " с помощью банковской карты " + cardNumber + " держателя " + cardHolder);
    }
}
package com;

public class PayPalPaymentStrategy implements IPaymentStrategy {
    private String email;

    public PayPalPaymentStrategy(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Оплачено " + amount + " через PayPal аккаунт " + email);
    }
}
package com;

public class PaymentContext {
    private IPaymentStrategy paymentStrategy;

    public void setPaymentStrategy(IPaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void pay(double amount) {
        if (paymentStrategy != null) {
            paymentStrategy.pay(amount);
        } else {
            System.out.println("Стратегия оплаты не выбрана");
        }
    }
}
package com;

public class Main {
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext();

        context.setPaymentStrategy(new CreditCardPaymentStrategy("1234-5678-9012-3456", "Иван Иванов"));
        context.pay(100.0);

        context.setPaymentStrategy(new PayPalPaymentStrategy("ivanov@example.com"));
        context.pay(200.0);

        context.setPaymentStrategy(new CryptoPaymentStrategy("1A2b3C4d5E6f7G8h9I0JkLmN"));
        context.pay(300.0);
    }
}

//ЗАДАЧА-2


package hw2;

public interface IObserver {
    void update(double exchangeRate);
}

package hw2;

public interface ISubject {
    void registerObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void notifyObservers();
}
package hw2;

public class AutoBuyObserver implements IObserver {
    private double threshold;

    public AutoBuyObserver(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public void update(double exchangeRate) {
        if (exchangeRate < threshold) {
            System.out.println(exchangeRate);
        }
    }
}
package hw2;

import java.util.ArrayList;
import java.util.List;

public class CurrencyExchange implements ISubject {
    private List<IObserver> observers;
    private double exchangeRate;

    public CurrencyExchange() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(exchangeRate);
        }
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
        notifyObservers();
    }
}

package hw2;

public class DisplayObserver implements IObserver {
    private String name;

    public DisplayObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(double exchangeRate) {
        System.out.println(exchangeRate);
    }
}
package hw2;

public class EmailNotificationObserver implements IObserver {
    private String email;

    public EmailNotificationObserver(String email) {
        this.email = email;
    }

    @Override
    public void update(double exchangeRate) {
        System.out.println("Отправка уведомления на " + email + ": Новый курс валюты: " + exchangeRate);
    }
}

package hw2;

public class Main {
    public static void main(String[] args) {
        CurrencyExchange currencyExchange = new CurrencyExchange();

        IObserver displayObserver = new DisplayObserver("Экран");
        IObserver autoBuyObserver = new AutoBuyObserver(70.0);
        IObserver emailObserver = new EmailNotificationObserver("example@mail.com");

        currencyExchange.registerObserver(displayObserver);
        currencyExchange.registerObserver(autoBuyObserver);
        currencyExchange.registerObserver(emailObserver);

        System.out.println("Изменение курса на 75.0:");
        currencyExchange.setExchangeRate(75.0);

        System.out.println("\nИзменение курса на 65.0:");
        currencyExchange.setExchangeRate(65.0);
    }
}




