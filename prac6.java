//ЗАДАЧА-1
package com.company;

import java.io.FileWriter;
import java.io.IOException;

enum LogLevel { INFO, WARNING, ERROR }

public class Logger {
    private static Logger logger;
    private static LogLevel level = LogLevel.INFO;
    private Logger() {}
    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }
    public void setLevel(LogLevel newLevel) {
        level = newLevel;
    }

    public void log(String message, LogLevel logLevel) {
        if (level == logLevel) {
            try (FileWriter writer = new FileWriter("C:\\Temp\\file.txt", true)) {
                writer.write(logLevel + " | " + message + System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

//ЗАДАЧА-2
package prac6_2;

public class Pizza {
    public String Size;
    public String Sauce;
    public String Cheese;

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getCheese() {
        return Cheese;
    }

    public void setCheese(String cheese) {
        Cheese = cheese;
    }

    public String getSauce() {
        return Sauce;
    }

    public void setSauce(String sauce) {
        Sauce = sauce;
    }
    public interface IpizzaBulder{
        void setSize();
        void setSauce();
        void setCheese();
        Pizza getPizza();
    }
    public class Margaritta implements IpizzaBulder{
        private Pizza pizza = new Pizza();

        @Override
        public void setCheese() {
            pizza.Cheese="Козий сыр";
        }

        @Override
        public void setSauce() {
            pizza.Sauce="Сырный";
        }

        @Override
        public void setSize() {
            pizza.Size="Большой";

        }

        @Override
        public Pizza getPizza() {
            return pizza;
        }
    }
    public class PizzaDirector{
        private IpizzaBulder ipizzaBulder;
        public PizzaDirector(IpizzaBulder ipizzaBulder){
            this.ipizzaBulder=ipizzaBulder;
        }
        public void ConstructorPizza(){
            ipizzaBulder.setSize();
            ipizzaBulder.setSauce();
            ipizzaBulder.setCheese();
        }
        public Pizza getPizza(){
            return  ipizzaBulder.getPizza();
        }
    }
}
