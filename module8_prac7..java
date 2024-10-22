//ЗАДАЧА-1
public interface ICommand {
    void execute();
    void undo();
}
public class Light {
    public void on() {
        System.out.println("Свет включен");
    }

    public void off() {
        System.out.println("Свет выключен");
    }
}
public class AirConditioner {
    public void on() {
        System.out.println("Кондиционер включен");
    }

    public void off() {
        System.out.println("Кондиционер выключен");
    }

    public void setTemperature(int temperature) {
        System.out.println("Температура установлена на " + temperature + " градусов");
    }
}
public class Television {
    public void on() {
        System.out.println("Телевизор включен");
    }

    public void off() {
        System.out.println("Телевизор выключен");
    }

    public void setChannel(int channel) {
        System.out.println("Канал установлен на " + channel);
    }
}
public class LightOnCommand implements ICommand {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}

public class LightOffCommand implements ICommand {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }
}
public class AirConditionerOnCommand implements ICommand {
    private AirConditioner ac;

    public AirConditionerOnCommand(AirConditioner ac) {
        this.ac = ac;
    }

    @Override
    public void execute() {
        ac.on();
    }

    @Override
    public void undo() {
        ac.off();
    }
}

public class AirConditionerOffCommand implements ICommand {
    private AirConditioner ac;

    public AirConditionerOffCommand(AirConditioner ac) {
        this.ac = ac;
    }

    @Override
    public void execute() {
        ac.off();
    }

    @Override
    public void undo() {
        ac.on();
    }
}
public class TelevisionOnCommand implements ICommand {
    private Television tv;

    public TelevisionOnCommand(Television tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.on();
    }

    @Override
    public void undo() {
        tv.off();
    }
}

public class TelevisionOffCommand implements ICommand {
    private Television tv;

    public TelevisionOffCommand(Television tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.off();
    }

    @Override
    public void undo() {
        tv.on();
    }
}
public class RemoteControl {
    private ICommand[] onCommands;
    private ICommand[] offCommands;
    private ICommand lastCommand;

    public RemoteControl() {
        onCommands = new ICommand[7];
        offCommands = new ICommand[7];

        ICommand noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }

        lastCommand = noCommand;
    }

    public void setCommand(int slot, ICommand onCommand, ICommand offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void pressOnButton(int slot) {
        onCommands[slot].execute();
        lastCommand = onCommands[slot];
    }

    public void pressOffButton(int slot) {
        offCommands[slot].execute();
        lastCommand = offCommands[slot];
    }

    public void pressUndoButton() {
        lastCommand.undo();
    }
}

class NoCommand implements ICommand {
    @Override
    public void execute() {
        System.out.println("Команда не назначена");
    }

    @Override
    public void undo() {
        System.out.println("Нечего отменять");
    }
}
public class MacroCommand implements ICommand {
    private ICommand[] commands;

    public MacroCommand(ICommand[] commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        for (ICommand command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        for (ICommand command : commands) {
            command.undo();
        }
    }
}
public class Main {
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();

        Light livingRoomLight = new Light();
        AirConditioner airConditioner = new AirConditioner();
        Television tv = new Television();

        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);

        AirConditionerOnCommand acOn = new AirConditionerOnCommand(airConditioner);
        AirConditionerOffCommand acOff = new AirConditionerOffCommand(airConditioner);

        TelevisionOnCommand tvOn = new TelevisionOnCommand(tv);
        TelevisionOffCommand tvOff = new TelevisionOffCommand(tv);

        remote.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remote.setCommand(1, acOn, acOff);
        remote.setCommand(2, tvOn, tvOff);

        remote.pressOnButton(0);
        remote.pressOnButton(1);
        remote.pressUndoButton();

        ICommand[] partyMode = {livingRoomLightOn, acOn, tvOn};
        MacroCommand partyOn = new MacroCommand(partyMode);

        remote.setCommand(3, partyOn, new NoCommand());
        remote.pressOnButton(3);
    }
}

//ЗАДАЧА-2
public abstract class ReportGenerator {

    public final void generateReport() {
        collectData();
        formatReport();
        if (customerWantsToSave()) {
            saveReport();
        }
    }

    protected abstract void collectData();
    protected abstract void formatReport();

    protected boolean customerWantsToSave() {
        return true;
    }

   public class PdfReport extends ReportGenerator {

    @Override
    protected void collectData() {
        System.out.println("Сбор данных для PDF-отчета...");
    }

    @Override
    protected void formatReport() {
        System.out.println("Форматирование отчета в PDF...");
    }

    @Override
    protected void saveReport() {
        System.out.println("Сохранение PDF-отчета...");
    }
}
public class ExcelReport extends ReportGenerator {

    @Override
    protected void collectData() {
        System.out.println("Сбор данных для Excel-отчета...");
    }

    @Override
    protected void formatReport() {
        System.out.println("Форматирование отчета в Excel...");
    }

    @Override
    protected void saveReport() {
        System.out.println("Сохранение Excel-отчета...");
    }

    @Override
    protected boolean customerWantsToSave() {
      
        System.out.println("Сохранить отчет в Excel? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input.equalsIgnoreCase("y");
    }
}
  public class CsvReport extends ReportGenerator {

    @Override
    protected void collectData() {
        System.out.println("Сбор данных для CSV-отчета...");
    }

    @Override
    protected void formatReport() {
        System.out.println("Форматирование отчета в CSV...");
    }

    @Override
    protected void saveReport() {
        System.out.println("Сохранение CSV-отчета...");
    }
}

public class HtmlReport extends ReportGenerator {

    @Override
    protected void collectData() {
        System.out.println("Сбор данных для HTML-отчета...");
    }

    @Override
    protected void formatReport() {
        System.out.println("Форматирование отчета в HTML...");
    }

    @Override
    protected void saveReport() {
        System.out.println("Сохранение HTML-отчета...");
    }
}
public class Main {
    public static void main(String[] args) {
        ReportGenerator pdfReport = new PdfReport();
        ReportGenerator excelReport = new ExcelReport();
        ReportGenerator htmlReport = new HtmlReport();

     
        System.out.println("Генерация PDF-отчета:");
        pdfReport.generateReport();
        
        System.out.println("\nГенерация Excel-отчета:");
        excelReport.generateReport();
        
        System.out.println("\nГенерация HTML-отчета:");
        htmlReport.generateReport();
    }
}
//ЗАДАЧА-3
public interface IMediator {
    void registerUser(IUser user);
    void sendMessage(String message, IUser user);
    void addUserToChannel(String channelName, IUser user);
    void sendMessageToChannel(String message, String channelName, IUser user);
}
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ChatMediator implements IMediator {
    private HashMap<String, Set<IUser>> channels;

    public ChatMediator() {
        this.channels = new HashMap<>();
    }

    @Override
    public void registerUser(IUser user) {
        System.out.println(user.getName() + " присоединился к системе.");
    }

    @Override
    public void addUserToChannel(String channelName, IUser user) {
        channels.putIfAbsent(channelName, new HashSet<>());
        channels.get(channelName).add(user);
        System.out.println(user.getName() + " добавлен в канал " + channelName);
    }

    @Override
    public void sendMessage(String message, IUser user) {
        System.out.println(user.getName() + ": " + message);
    }

    @Override
    public void sendMessageToChannel(String message, String channelName, IUser user) {
        if (channels.containsKey(channelName)) {
            if (channels.get(channelName).contains(user)) {
                for (IUser channelUser : channels.get(channelName)) {
                    if (channelUser != user) {
                        channelUser.receiveMessage(message, user);
                    }
                }
            } else {
                System.out.println(user.getName() + " не состоит в канале " + channelName);
            }
        } else {
            System.out.println("Канал " + channelName + " не существует.");
        }
    }
}
public interface IUser {
    void sendMessage(String message);
    void sendMessageToChannel(String message, String channelName);
    void receiveMessage(String message, IUser from);
    String getName();
}
public class User implements IUser {
    private String name;
    private IMediator mediator;

    public User(String name, IMediator mediator) {
        this.name = name;
        this.mediator = mediator;
        mediator.registerUser(this);
    }

    @Override
    public void sendMessage(String message) {
        mediator.sendMessage(message, this);
    }

    @Override
    public void sendMessageToChannel(String message, String channelName) {
        mediator.sendMessageToChannel(message, channelName, this);
    }

    @Override
    public void receiveMessage(String message, IUser from) {
        System.out.println(this.name + " получил сообщение от " + from.getName() + ": " + message);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
public class ChannelMediator extends ChatMediator {
    @Override
    public void sendMessageToChannel(String message, String channelName, IUser user) {
        super.sendMessageToChannel(message, channelName, user);
    }
}
public class ChatApp {
    public static void main(String[] args) {
        IMediator mediator = new ChannelMediator();

        IUser user1 = new User("Alice", mediator);
        IUser user2 = new User("Bob", mediator);
        IUser user3 = new User("Charlie", mediator);

        mediator.addUserToChannel("general", user1);
        mediator.addUserToChannel("general", user2);

        user1.sendMessageToChannel("Пр

  public class Main {
    public static void main(String[] args) {
        IMediator mediator = new ChannelMediator();

        IUser user1 = new User("Alice", mediator);
        IUser user2 = new User("Bob", mediator);
        IUser user3 = new User("Charlie", mediator);

        mediator.addUserToChannel("general", user1);
        mediator.addUserToChannel("general", user2);

        user1.sendMessageToChannel("Привет всем в канале general!", "general");

        mediator.addUserToChannel("sports", user2);
        mediator.addUserToChannel("sports", user3);

        user2.sendMessageToChannel("Новости спорта!", "sports");
        user3.sendMessageToChannel("Привет из sports!", "sports");
    }
}

