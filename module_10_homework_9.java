
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
