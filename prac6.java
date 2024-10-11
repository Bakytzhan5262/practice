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


//ЗАДАЧА-3
public interface IReportBuilder {
    void setHeader(String header);
    void setContent(String content);
    void setFooter(String footer);
    void addSection(String sectionName, String sectionContent);
    void setStyle(ReportStyle style);
    Report getReport();
}
public class TextReportBuilder implements IReportBuilder {
    private Report report;

    public TextReportBuilder() {
        this.report = new Report();
    }

    @Override
    public void setHeader(String header) {
        report.setHeader(header);
    }

    @Override
    public void setContent(String content) {
        report.setContent(content);
    }

    @Override
    public void setFooter(String footer) {
        report.setFooter(footer);
    }

    @Override
    public void addSection(String sectionName, String sectionContent) {
        report.addSection(sectionName, sectionContent);
    }

    @Override
    public void setStyle(ReportStyle style) {
        report.setStyle(style);
    }

    @Override
    public Report getReport() {
        return this.report;
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
    public void addSection(String sectionName, String sectionContent) {
        report.addSection("<h2>" + sectionName + "</h2>", "<p>" + sectionContent + "</p>");
    }

    @Override
    public void setStyle(ReportStyle style) {
        report.setStyle(style);
    }

    @Override
    public Report getReport() {
        return this.report;
    }
}
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfReportBuilder implements IReportBuilder {
    private Report report;

    public PdfReportBuilder() {
        this.report = new Report();
    }

    @Override
    public void setHeader(String header) {
        report.setHeader(header);
    }

    @Override
    public void setContent(String content) {
        report.setContent(content);
    }

    @Override
    public void setFooter(String footer) {
        report.setFooter(footer);
    }

    @Override
    public void addSection(String sectionName, String sectionContent) {
        report.addSection(sectionName, sectionContent);
    }

    @Override
    public void setStyle(ReportStyle style) {
        report.setStyle(style);
    }

    @Override
    public Report getReport() {
        return this.report;
    }

    public void exportToPdf(String filePath) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        document.add(new Paragraph(report.getHeader()));
        document.add(new Paragraph(report.getContent()));
        for (var entry : report.getSections().entrySet()) {
            document.add(new Paragraph(entry.getKey()));
            document.add(new Paragraph(entry.getValue()));
        }
        document.add(new Paragraph(report.getFooter()));
        document.close();
    }
}
public class ReportStyle {
    private String backgroundColor;
    private String fontColor;
    private int fontSize;

    public ReportStyle(String backgroundColor, String fontColor, int fontSize) {
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
        this.fontSize = fontSize;
    }

    
}

import java.util.HashMap;
import java.util.Map;

public class Report {
    private String header;
    private String content;
    private String footer;
    private Map<String, String> sections = new HashMap<>();
    private ReportStyle style;

    public void setHeader(String header) {
        this.header = header;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public void addSection(String sectionName, String sectionContent) {
        this.sections.put(sectionName, sectionContent);
    }

    public void setStyle(ReportStyle style) {
        this.style = style;
    }

    public String getHeader() {
        return header;
    }

    public String getContent() {
        return content;
    }

    public String getFooter() {
        return footer;
    }

    public Map<String, String> getSections() {
        return sections;
    }

    public void export() {
    
        System.out.println("Header: " + header);
        System.out.println("Content: " + content);
        for (var entry : sections.entrySet()) {
            System.out.println("Section: " + entry.getKey() + " - " + entry.getValue());
        }
        System.out.println("Footer: " + footer);
    }
}
public class ReportDirector {
    public void constructReport(IReportBuilder builder, ReportStyle style) {
        builder.setHeader("Отчет");
        builder.setContent("Это основной контент отчета.");
        builder.addSection("Раздел 1", "Содержание раздела 1");
        builder.addSection("Раздел 2", "Содержание раздела 2");
        builder.setFooter("Это подвал отчета.");
        builder.setStyle(style);
    }
}
public class Main {
    public static void main(String[] args) {
        ReportDirector director = new ReportDirector();
        
        TextReportBuilder textBuilder = new TextReportBuilder();
        director.constructReport(textBuilder, new ReportStyle("white", "black", 12));
        Report textReport = textBuilder.getReport();
        textReport.export();

        HtmlReportBuilder htmlBuilder = new HtmlReportBuilder();
        director.constructReport(htmlBuilder, new ReportStyle("white", "black", 12));
        Report htmlReport = htmlBuilder.getReport();
        htmlReport.export();
        
        PdfReportBuilder pdfBuilder = new PdfReportBuilder();
        director.constructReport(pdfBuilder, new ReportStyle("white", "black", 12));
        try {
            pdfBuilder.exportToPdf("report.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//ЗАДАЧА-4
public interface ICloneable<T> {
    T clone();
}
public class Skill implements ICloneable<Skill> {
    private String name;
    private int power;

    public Skill(String name, int power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    @Override
    public Skill clone() {
        return new Skill(this.name, this.power); 
    }

    @Override
    public String toString() {
        return "Skill{name='" + name + "', power=" + power + "}";
    }
}

public class Weapon implements ICloneable<Weapon> {
    private String name;
    private int damage;

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public Weapon clone() {
        return new Weapon(this.name, this.damage);  
    }

    @Override
    public String toString() {
        return "Weapon{name='" + name + "', damage=" + damage + "}";
    }
}
public class Armor implements ICloneable<Armor> {
    private String name;
    private int defense;

    public Armor(String name, int defense) {
        this.name = name;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public int getDefense() {
        return defense;
    }

    @Override
    public Armor clone() {
        return new Armor(this.name, this.defense);  
    }

    @Override
    public String toString() {
        return "Armor{name='" + name + "', defense=" + defense + "}";
    }
}
import java.util.ArrayList;
import java.util.List;

public class Character implements ICloneable<Character> {
    private String name;
    private int health;
    private int strength;
    private int agility;
    private int intelligence;

    private Weapon weapon;
    private Armor armor;
    private List<Skill> skills;

    public Character(String name, int health, int strength, int agility, int intelligence, Weapon weapon, Armor armor) {
        this.name = name;
        this.health = health;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.weapon = weapon;
        this.armor = armor;
        this.skills = new ArrayList<>();
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    @Override
    public Character clone() {
        Character clonedCharacter = new Character(
                this.name, this.health, this.strength, this.agility, this.intelligence,
                this.weapon.clone(), this.armor.clone()  
        );

        for (Skill skill : this.skills) {
            clonedCharacter.addSkill(skill.clone());  
        }

        return clonedCharacter;
    }

    @Override
    public String toString() {
        return "Character{name='" + name + "', health=" + health +
               ", strength=" + strength + ", agility=" + agility +
               ", intelligence=" + intelligence + ", weapon=" + weapon +
               ", armor=" + armor + ", skills=" + skills + "}";
    }
}
public class Main {
    public static void main(String[] args) {
        Weapon sword = new Weapon("Sword", 50);
        Armor shield = new Armor("Shield", 40);

        Character originalCharacter = new Character("Warrior", 100, 20, 15, 10, sword, shield);
        originalCharacter.addSkill(new Skill("Slash", 30));
        originalCharacter.addSkill(new Skill("Block", 20));

        Character clonedCharacter = originalCharacter.clone();

        clonedCharacter.addSkill(new Skill("Berserk", 50));

        System.out.println("Original Character: " + originalCharacter);
        System.out.println("Cloned Character: " + clonedCharacter);
    }
}
