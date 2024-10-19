package com.company;

public interface ICostCalculationStrategy {
    public double calculateCost(int passengers, int typeClass, boolean bag, boolean isChild, boolean isSenior);
}

package com.company;

public class BusTravel implements ICostCalculationStrategy {
    private double baseCost;

    @Override
    public double calculateCost(int passengers, int typeClass, boolean bag, boolean isChild, boolean isSenior) {
        switch (typeClass) {
            case 1: baseCost = 20; break;
            case 2: baseCost = 40; break;
            case 3: baseCost = 60; break;
            default: baseCost = 10; break;
        }

        double discount = 1.0;
        if (isChild) discount = 0.5;
        if (isSenior) discount = 0.7;

        double bagCost = bag ? 5 : 0;

        return passengers * (baseCost * discount + bagCost);
    }
}

package com.company;

public class AirplaneTravel implements ICostCalculationStrategy {
    private double baseCost;

    @Override
    public double calculateCost(int passengers, int typeClass, boolean bag, boolean isChild, boolean isSenior) {
        switch (typeClass) {
            case 1: baseCost = 100; break;
            case 2: baseCost = 150; break;
            case 3: baseCost = 200; break;
            default: baseCost = 130; break;
        }

        double discount = 1.0;
        if (isChild) discount = 0.5;
        if (isSenior) discount = 0.8;

        double bagCost = bag ? 30 : 0;

        return passengers * (baseCost * discount + bagCost);
    }
}

package com.company;

public class TrainTravel implements ICostCalculationStrategy {
    private double baseCost;

    @Override
    public double calculateCost(int passengers, int typeClass, boolean bag, boolean isChild, boolean isSenior) {
        switch (typeClass) {
            case 1: baseCost = 50; break;
            case 2: baseCost = 80; break;
            case 3: baseCost = 120; break;
            default: baseCost = 40; break;
        }

        double discount = 1.0;
        if (isChild) discount = 0.6;
        if (isSenior) discount = 0.75;

        double bagCost = bag ? 10 : 0;

        return passengers * (baseCost * discount + bagCost);
    }
}


package com.company;

public class Travel_Context {
    private ICostCalculationStrategy calculation;

    public void setCalculationStrategy(ICostCalculationStrategy calculation) {
        this.calculation = calculation;
    }

    public double calculateTravelCost(int passengers, int typeClass, boolean bag, boolean isChild, boolean isSenior) {
        if (calculation == null) {
            throw new IllegalArgumentException("Не установлена стратегия расчета.");
        }
        return calculation.calculateCost(passengers, typeClass, bag, isChild, isSenior);
    }
}


package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Travel_Context travelContext = new Travel_Context();

        System.out.println("Выберите тип транспорта: 1 - Автобус, 2 - Самолет, 3 - Поезд");
        int transportType = scanner.nextInt();

        switch (transportType) {
            case 1:
                travelContext.setCalculationStrategy(new BusTravel());
                break;
            case 2:
                travelContext.setCalculationStrategy(new AirplaneTravel());
                break;
            case 3:
                travelContext.setCalculationStrategy(new TrainTravel());
                break;
            default:
                System.out.println("Некорректный выбор!");
                return;
        }

        System.out.println("Введите количество пассажиров:");
        int passengers = scanner.nextInt();

        System.out.println("Выберите класс: 1 - Эконом, 2 - Бизнес, 3 - Первый");
        int typeClass = scanner.nextInt();

        System.out.println("Есть ли багаж? (true/false)");
        boolean bag = scanner.nextBoolean();

        System.out.println("Есть ли ребенок? (true/false)");
        boolean isChild = scanner.nextBoolean();

        System.out.println("Есть ли пенсионер? (true/false)");
        boolean isSenior = scanner.nextBoolean();

        double cost = travelContext.calculateTravelCost(passengers, typeClass, bag, isChild, isSenior);
        System.out.println("Стоимость поездки: " + cost + " рублей");
    }
}

//ЗАДАЧА-2

package com.company;

public interface IObserver {
    void update(String stockSymbol, double price);
}
package com.company;

public interface ISubject {
    void registerObserver(IObserver observer, String stockSymbol);
    void removeObserver(IObserver observer, String stockSymbol);
    void notifyObservers(String stockSymbol);
}

package com.company;

public class TradingRobot implements IObserver {
    private String robotName;
    private double buyThreshold;
    private double sellThreshold;

    public TradingRobot(String robotName, double buyThreshold, double sellThreshold) {
        this.robotName = robotName;
        this.buyThreshold = buyThreshold;
        this.sellThreshold = sellThreshold;
    }

    @Override
    public void update(String stockSymbol, double price) {
        if (price < buyThreshold) {
            System.out.println(robotName + " покупает акцию " + stockSymbol + " по цене " + price);
        } else if (price > sellThreshold) {
            System.out.println(robotName + " продает акцию " + stockSymbol + " по цене " + price);
        } else {
            System.out.println(robotName + " наблюдает за акцией " + stockSymbol + ", текущая цена " + price);
        }
    }
}
package com.company;

public class Trader implements IObserver {
    private String name;

    public Trader(String name) {
        this.name = name;
    }

    @Override
    public void update(String stockSymbol, double price) {
        System.out.println(name + " получил обновление: акция " + stockSymbol + " стоит " + price);
    }
}
package com.company;

public class StockExchange implements ISubject {
    private static final int MAX_STOCKS = 10;
    private static final int MAX_OBSERVERS = 10;

    private String[] stockSymbols = new String[MAX_STOCKS];
    private double[] stockPrices = new double[MAX_STOCKS];

    private IObserver[][] observers = new IObserver[MAX_STOCKS][MAX_OBSERVERS];
    private int[] observerCounts = new int[MAX_STOCKS];

    private int stockCount = 0;


    public void addStock(String stockSymbol, double initialPrice) {
        if (stockCount < MAX_STOCKS) {
            stockSymbols[stockCount] = stockSymbol;
            stockPrices[stockCount] = initialPrice;
            stockCount++;
        }
    }

    private int findStockIndex(String stockSymbol) {
        for (int i = 0; i < stockCount; i++) {
            if (stockSymbols[i].equals(stockSymbol)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void registerObserver(IObserver observer, String stockSymbol) {
        int stockIndex = findStockIndex(stockSymbol);
        if (stockIndex != -1 && observerCounts[stockIndex] < MAX_OBSERVERS) {
            observers[stockIndex][observerCounts[stockIndex]++] = observer;
        }
    }

    @Override
    public void removeObserver(IObserver observer, String stockSymbol) {
        int stockIndex = findStockIndex(stockSymbol);
        if (stockIndex != -1) {
            for (int i = 0; i < observerCounts[stockIndex]; i++) {
                if (observers[stockIndex][i] == observer) {
                    for (int j = i; j < observerCounts[stockIndex] - 1; j++) {
                        observers[stockIndex][j] = observers[stockIndex][j + 1];
                    }
                    observerCounts[stockIndex]--;
                    break;
                }
            }
        }
    }

    @Override
    public void notifyObservers(String stockSymbol) {
        int stockIndex = findStockIndex(stockSymbol);
        if (stockIndex != -1) {
            for (int i = 0; i < observerCounts[stockIndex]; i++) {
                observers[stockIndex][i].update(stockSymbol, stockPrices[stockIndex]);
            }
        }
    }

    public void setStockPrice(String stockSymbol, double newPrice) {
        int stockIndex = findStockIndex(stockSymbol);
        if (stockIndex != -1) {
            stockPrices[stockIndex] = newPrice;
            notifyObservers(stockSymbol);
        }
    }
}
package com.company;

public class Main {
    public static void main(String[] args) {
        StockExchange stockExchange = new StockExchange();
        
        stockExchange.addStock("AAPL", 120.0);
        stockExchange.addStock("GOOGL", 130.0);
        
        Trader trader1 = new Trader("Трейдер 1");
        Trader trader2 = new Trader("Трейдер 2");
        TradingRobot robot1 = new TradingRobot("Робот 1", 100, 150);
        
        stockExchange.registerObserver(trader1, "AAPL");
        stockExchange.registerObserver(trader2, "AAPL");
        stockExchange.registerObserver(robot1, "GOOGL");
        
        stockExchange.setStockPrice("AAPL", 125.0);
        stockExchange.setStockPrice("GOOGL", 140.0);

        stockExchange.removeObserver(trader2, "AAPL");
        
        stockExchange.setStockPrice("AAPL", 160.0);
        stockExchange.setStockPrice("GOOGL", 90.0);
    }
}


