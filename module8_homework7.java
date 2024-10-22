//ЗАДАЧА-1
public interface ICommand {
    void execute();
    void undo();
}
public class Light {
    public void turnOn() {
        System.out.println("Свет включен.");
    }

    public void turnOff() {
        System.out.println("Свет выключен.");
    }
}
public class Door {
    public void open() {
        System.out.println("Дверь открыта.");
    }

    public void close() {
        System.out.println("Дверь закрыта.");
    }
}
public class Thermostat {
    private int temperature;

    public void increaseTemperature() {
        temperature++;
        System.out.println("Температура увеличена до: " + temperature + "°C.");
    }

    public void decreaseTemperature() {
        temperature--;
        System.out.println("Температура уменьшена до: " + temperature + "°C.");
    }
}
public class LightOnCommand implements ICommand {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }

    @Override
    public void undo() {
        light.turnOff();
    }
}

public class LightOffCommand implements ICommand {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }

    @Override
    public void undo() {
        light.turnOn();
    }
}
public class DoorOpenCommand implements ICommand {
    private Door door;

    public DoorOpenCommand(Door door) {
        this.door = door;
    }

    @Override
    public void execute() {
        door.open();
    }

    @Override
    public void undo() {
        door.close();
    }
}

public class DoorCloseCommand implements ICommand {
    private Door door;

    public DoorCloseCommand(Door door) {
        this.door = door;
    }

    @Override
    public void execute() {
        door.close();
    }

    @Override
    public void undo() {
        door.open();
    }
}
public class IncreaseTemperatureCommand implements ICommand {
    private Thermostat thermostat;

    public IncreaseTemperatureCommand(Thermostat thermostat) {
        this.thermostat = thermostat;
    }

    @Override
    public void execute() {
        thermostat.increaseTemperature();
    }

    @Override
    public void undo() {
        thermostat.decreaseTemperature();
    }
}

public class DecreaseTemperatureCommand implements ICommand {
    private Thermostat thermostat;

    public DecreaseTemperatureCommand(Thermostat thermostat) {
        this.thermostat = thermostat;
    }

    @Override
    public void execute() {
        thermostat.decreaseTemperature();
    }

    @Override
    public void undo() {
        thermostat.increaseTemperature();
    }
}
import java.util.Stack;

public class RemoteControl {
    private ICommand command;
    private Stack<ICommand> history;

    public RemoteControl() {
        history = new Stack<>();
    }

    public void setCommand(ICommand command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
        history.push(command);
    }

    public void pressUndo() {
        if (!history.isEmpty()) {
            ICommand lastCommand = history.pop();
            lastCommand.undo();
        } else {
            System.out.println("Нет команд для отмены.");
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Light livingRoomLight = new Light();
        Door frontDoor = new Door();
        Thermostat thermostat = new Thermostat();

        LightOnCommand lightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand lightOff = new LightOffCommand(livingRoomLight);
        DoorOpenCommand doorOpen = new DoorOpenCommand(frontDoor);
        DoorCloseCommand doorClose = new DoorCloseCommand(frontDoor);
        IncreaseTemperatureCommand increaseTemp = new IncreaseTemperatureCommand(thermostat);
        DecreaseTemperatureCommand decreaseTemp = new DecreaseTemperatureCommand(thermostat);
        RemoteControl remote = new RemoteControl();

        remote.setCommand(lightOn);
        remote.pressButton();
        remote.setCommand(lightOff);
        remote.pressButton();
        remote.pressUndo();

        remote.setCommand(doorOpen);
        remote.pressButton();
        remote.setCommand(doorClose);
        remote.pressButton();
        remote.pressUndo();

        remote.setCommand(increaseTemp);
        remote.pressButton();
        remote.setCommand(decreaseTemp);
        remote.pressButton();
        remote.pressUndo();
    }
}

//ЗАДАЧА-2
public abstract class Beverage {
    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments()) {
            addCondiments();
        }
    }

    private void boilWater() {
        System.out.println("Кипячение воды...");
    }

    private void pourInCup() {
        System.out.println("Наливание в чашку...");
    }

    protected abstract void brew();
    protected abstract void addCondiments();

    protected boolean customerWantsCondiments() {
        return true

public class Tea extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Заваривание чая...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Добавление лимона...");
    }
}
import java.util.Scanner;

public class Coffee extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Заваривание кофе...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Добавление сахара и молока...");
    }

    @Override
    protected boolean customerWantsCondiments() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Вы хотите добавить сахар и молоко в кофе (да/нет)?");
        String answer = scanner.nextLine();
        return answer.toLowerCase().equals("да");
    }
}
      public class HotChocolate extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Подогрев шоколада...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Добавление маршмэллоу...");
    }
}
public class HotChocolate extends Beverage {
    @Override
    protected void brew() {
        System.out.println("Подогрев шоколада...");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Добавление маршмэллоу...");
    }

    @Override
    public void prepareRecipe() {
        boilWater();
        pourInCup();
        brew();
        if (customerWantsCondiments()) {
            addCondiments();
        }
    }
}

public class BeverageTestDrive {
    public static void main(String[] args) {
        Beverage tea = new Tea();
        System.out.println("Приготовление чая:");
        tea.prepareRecipe();

        System.out.println("\nПриготовление кофе:");
        Beverage coffee = new Coffee();
        coffee.prepareRecipe();
    }
}

//ЗАДАЧА-3
public interface IMediator {
    void sendMessage(String message, User sender);
    void addUser(User user);
}
import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements IMediator {
    private List<User> users;

    public ChatRoom() {
        this.users = new ArrayList<>();
    }

    @Override
    public void addUser(User user) {
        users.add(user);
        notifyUserJoined(user);
    }

    @Override
    public void sendMessage(String message, User sender) {
        for (User user : users) {
            if (user != sender) {
                user.receive(message, sender);
            }
        }
    }

    private void notifyUserJoined(User newUser) {
        for (User user : users) {
            if (user != newUser) {
                user.receiveSystemMessage(newUser.getName() + " присоединился к чату.");
            }
        }
    }

    public void notifyUserLeft(User leavingUser) {
        users.remove(leavingUser);
        for (User user : users) {
            user.receiveSystemMessage(leavingUser.getName() + " покинул чат.");
        }
    }
}
public abstract class User {
    protected IMediator mediator;
    protected String name;

    public User(IMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void send(String message) {
        System.out.println(this.name + " отправляет сообщение: " + message);
        mediator.sendMessage(message, this);
    }

    public abstract void receive(String message, User sender);

    public abstract void receiveSystemMessage(String message);
}
public class BasicUser extends User {
    public BasicUser(IMediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void receive(String message, User sender) {
        System.out.println(this.name + " получил сообщение от " + sender.getName() + ": " + message);
    }

    @Override
    public void receiveSystemMessage(String message) {
        System.out.println(this.name + " получил системное сообщение: " + message);
    }
}
public class ChatMediatorDemo {
    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoom();

        User biba = new BasicUser(chatRoom, "Биба");
        User baha = new BasicUser(chatRoom, "Баха");
        User uali = new BasicUser(chatRoom, "Уали");

        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);

        biba.send("LOL");
        baha.send("KEKW");

        chatRoom.notifyUserLeft(uali);

        alice.send("GG");
    }
}

  
