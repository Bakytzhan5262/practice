public class Author {
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
import java.util.List;

public class Book {
    private String title;
    private String isbn;
    private List<Author> authors;
    private int publicationYear;
    private boolean availabilityStatus; 

    public Book(String title, String isbn, List<Author> authors, int publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
        this.publicationYear = publicationYear;
        this.availabilityStatus = true; 
    }

    public void changeAvailabilityStatus(boolean status) {
        this.availabilityStatus = status;
    }

    public void getBookInfo() {
        System.out.println("Title: " + title + ", ISBN: " + isbn + ", Authors: " + 
                authors.stream().map(Author::getName).reduce((a, b) -> a + ", " + b).orElse("No authors") +
                ", Year: " + publicationYear + ", Available: " + availabilityStatus);
    }

    public boolean isAvailable() {
        return availabilityStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public int getPublicationYear() {
        return publicationYear;
    }
}
public class User {
    private String id;
    private String name;
    private String email;
    private UserType userType;

    public User(String id, String name, String email, UserType userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }
}
public enum UserType {
    READER,
    LIBRARIAN
}
import java.time.LocalDate;

public class Loan {
    private Book book;
    private User reader;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(Book book, User reader, LocalDate loanDate, LocalDate returnDate) {
        this.book = book;
        this.reader = reader;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public void issueLoan() {
        System.out.println("Issuing loan for book: " + book.getTitle() + " to " + reader.getName());
        book.changeAvailabilityStatus(false); 
    }

    public void returnBook() {
        System.out.println("Returning book: " + book.getTitle() + " from " + reader.getName());
        book.changeAvailabilityStatus(true); 
    }

    public Book getBook() {
        return book;
    }

    public User getReader() {
        return reader;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
import java.util.List;

public class Library {
    private List<Book> books;
    private List<User> users;

    public Library(List<Book> books, List<User> users) {
        this.books = books;
        this.users = users;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerUser(User user) {
        users.add(user);
    }
    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }
}
import java.util.List;

public class Report {
    public void generatePopularBooksReport(List<Book> books) {
        System.out.println("Most popular books:");
        books.forEach(book -> System.out.println(book.getTitle()));
    }

    public void generateReaderActivityReport(List<User> users) {
        System.out.println("Reader activity report:");
        users.forEach(user -> System.out.println(user.getName()));
    }
}

