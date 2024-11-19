
import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        File file1 = new File("File1.txt", 150);
        File file2 = new File("File2.txt", 200);

        Directory root = new Directory("Root");
        Directory subDir = new Directory("SubDirectory");
        File subFile1 = new File("SubFile1.txt", 50);

        root.add(file1);
        root.add(file2);
        subDir.add(subFile1);
        root.add(subDir);
        root.display(1);

        System.out.println("Size of the root directory is: " + root.getSize() + " bytes");
    }
}

abstract class FileSystemComponent {
    protected String name;

    public FileSystemComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void display(int depth);

    public abstract long getSize();
}

class File extends FileSystemComponent {
    private long size;

    public File(String name, long size) {
        super(name);
        this.size = size;
    }

    @Override
    public void display(int depth) {
        System.out.println("-".repeat(depth) + " File: " + name + " [Size: " + size + " bytes]");
    }

    @Override
    public long getSize() {
        return size;
    }
}

class Directory extends FileSystemComponent {
    private List<FileSystemComponent> children = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    public void add(FileSystemComponent component) {
        if (!children.contains(component)) {
            children.add(component);
            System.out.println("Added " + component.getClass().getSimpleName() + ": " + component.getName());
        } else {
            System.out.println(component.getName() + " is already added.");
        }
    }

    public void remove(FileSystemComponent component) {
        if (children.contains(component)) {
            children.remove(component);
            System.out.println("Removed " + component.getClass().getSimpleName() + ": " + component.getName());
        } else {
            System.out.println(component.getName() + " not found to remove.");
        }
    }

    @Override
    public void display(int depth) {
        System.out.println("-".repeat(depth) + " Directory: " + name);
        for (FileSystemComponent component : children) {
            component.display(depth + 2);
        }
    }

    @Override
    public long getSize() {
        long totalSize = 0;
        for (FileSystemComponent component : children) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
}





public class Program {
    public static void main(String[] args) {
        TV tv = new TV();
        AudioSystem audio = new AudioSystem();
        DVDPlayer dvdPlayer = new DVDPlayer();
        GameConsole gameConsole = new GameConsole();

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(tv, audio, dvdPlayer, gameConsole);

        homeTheater.watchMovie();
        System.out.println();

        homeTheater.startGame();
        System.out.println();

        homeTheater.listenToMusic();
        System.out.println();

        homeTheater.turnOffSystem();
    }
}

class TV {
    public void turnOn() {
        System.out.println("TV is turned on.");
    }

    public void turnOff() {
        System.out.println("TV is turned off.");
    }

    public void selectChannel(int channel) {
        System.out.println("TV channel set to " + channel + ".");
    }
}

class AudioSystem {
    public void turnOn() {
        System.out.println("Audio system is turned on.");
    }

    public void setVolume(int level) {
        System.out.println("Audio volume set to " + level + ".");
    }

    public void turnOff() {
        System.out.println("Audio system is turned off.");
    }
}

class DVDPlayer {
    public void play(String name) {
        System.out.println("DVD is playing - " + name);
    }

    public void pause() {
        System.out.println("DVD is paused.");
    }

    public void stop() {
        System.out.println("DVD is stopped.");
    }
}

class GameConsole {
    public void turnOn() {
        System.out.println("Game console is turned on.");
    }

    public void startGame(String game) {
        System.out.println("Starting game: " + game);
    }
}

class HomeTheaterFacade {
    private TV tv;
    private AudioSystem audioSystem;
    private DVDPlayer dvdPlayer;
    private GameConsole gameConsole;

    public HomeTheaterFacade(TV tv, AudioSystem audioSystem, DVDPlayer dvdPlayer, GameConsole gameConsole) {
        this.tv = tv;
        this.audioSystem = audioSystem;
        this.dvdPlayer = dvdPlayer;
        this.gameConsole = gameConsole;
    }

    public void watchMovie() {
        System.out.println("Setting up to watch a movie...");
        tv.turnOn();
        audioSystem.turnOn();
        audioSystem.setVolume(10);
        dvdPlayer.play("Openheimer");
        System.out.println("Movie is now playing.");
    }

    public void startGame() {
        System.out.println("Setting up to start a game...");
        tv.turnOn();
        audioSystem.turnOn();
        audioSystem.setVolume(8);
        gameConsole.turnOn();
        gameConsole.startGame("Riders Republic");
        System.out.println("Game has started.");
    }

    public void listenToMusic() {
        System.out.println("Setting up to listen to music...");
        tv.turnOn();
        audioSystem.turnOn();
        audioSystem.setVolume(6);
        System.out.println("Music is now playing through TV and audio system.");
    }

    public void turnOffSystem() {
        System.out.println("Shutting down the home theater system...");
        dvdPlayer.stop();
        tv.turnOff();
        audioSystem.turnOff();
        gameConsole.turnOn();
        System.out.println("System is now off.");
    }
}
