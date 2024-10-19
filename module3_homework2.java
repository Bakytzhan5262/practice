public void log(String level, String message) {
    System.out.println(level + ": " + message);
}
1-задача
public void logError(String message) {
    log("ERROR", message);
}

public void logWarning(String message) {
    log("WARNING", message);
}

public void logInfo(String message) {
    log("INFO", message);
}



2-задача
public class Config {
    public static final String CONNECTION_STRING = "Server=myServer;Database=myDb;User Id=myUser;Password=myPass;";
}

public class DatabaseService {
    public void connect() {
        System.out.println("Подключение к базе данных с использованием строки: " + Config.CONNECTION_STRING);
    }
}

public class LoggingService {
    public void log(String message) {
        System.out.println("Запись лога в базу данных с использованием строки: " + Config.CONNECTION_STRING);
}


3-задача
public void processNumbers(int[] numbers) {
    if (numbers == null || numbers.length == 0) {
        return;
    }

    for (int number : numbers) {
        if (number > 0) {
            System.out.println(number);
        }
    }
}


4-задача
public void printPositiveNumbers(int[] numbers) {
    Arrays.sort(numbers);

    for (int number : numbers) {
        if (number > 0) {
            System.out.println(number);
        }
    }
}

5-задача
public int divide(int a, int b) {
    if (b == 0) {
        return 0; 
    }
    return a / b;
}

6-задача
public class User {
    private String name;
    private String email;
    private String address;

    // Геттеры и сеттеры для полей
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


7-задача
public class FileReader {

    public String readFile(String filePath) {
        return "file content";
    }
}

8-задача
public class ReportGenerator {

    public void generatePdfReport() {

    }
}


