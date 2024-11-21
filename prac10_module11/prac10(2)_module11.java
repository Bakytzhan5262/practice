public class Book {
    private String title;
    private String author;
    private String genre;
    private String isbn;

    public Book(String title, String author, String genre, String isbn) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() {
        return isbn;
    }
}
public class Reader {
    private String name;
    private String lastName;
    private int ticketNumber;

    public Reader(String name, String lastName, int ticketNumber) {
        this.name = name;
        this.lastName = lastName;
        this.ticketNumber = ticketNumber;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }
}
public class Librarian {
    public void issueBook(Book book, Reader reader) {
        System.out.println("Книга \"" + book.getTitle() + "\" выдана читателю " + reader.getName());
    }

    public void returnBook(Book book, Reader reader) {
        System.out.println("Книга \"" + book.getTitle() + "\" возвращена читателем " + reader.getName());
    }
}
import java.util.List;
import java.util.stream.Collectors;

public class Catalog {
    private List<Book> books;

    public Catalog(List<Book> books) {
        this.books = books;
    }

    public List<Book> searchBooks(String searchTerm) {
        return books.stream()
            .filter(book -> book.getTitle().contains(searchTerm) || book.getAuthor().contains(searchTerm))
            .collect(Collectors.toList());
    }

    public List<Book> filterByAuthor(String author) {
        return books.stream()
            .filter(book -> book.getAuthor().equals(author))
            .collect(Collectors.toList());
    }

    public List<Book> filterByGenre(String genre) {
        return books.stream()
            .filter(book -> book.getGenre().equals(genre))
            .collect(Collectors.toList());
    }
}
import java.util.HashMap;
import java.util.Map;

public class AccountingSystem {
    private Map<Book, Reader> loanHistory = new HashMap<>();

    public void recordLoan(Book book, Reader reader) {
        loanHistory.put(book, reader);
        System.out.println("Книга \"" + book.getTitle() + "\" выдана читателю " + reader.getName());
    }

    public void recordReturn(Book book, Reader reader) {
        loanHistory.remove(book);
        System.out.println("Книга \"" + book.getTitle() + "\" возвращена читателем " + reader.getName());
    }

    public void getLoanHistory() {
        loanHistory.forEach((book, reader) -> System.out.println(book.getTitle() + " - " + reader.getName()));
    }
}
public interface CatalogInterface {
    List<Book> searchBooks(String searchTerm);
    List<Book> filterByAuthor(String author);
    List<Book> filterByGenre(String genre);
}
public interface AccountingSystemInterface {
    void recordLoan(Book book, Reader reader);
    void recordReturn(Book book, Reader reader);
    void getLoanHistory();
}
