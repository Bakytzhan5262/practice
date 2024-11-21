package t1;

public class Book {
    private String title;
    private String author;
    private int ISBN;
    private boolean status;
    public Book(String title, String author, int ISBN, boolean status) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.status = status;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getISBN() {
        return ISBN;
    }
    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ISBN=" + ISBN +
                ", status=" + status +
                '}';
    }
}


package t1;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;

  public Library() {
      books = new ArrayList<Book>();
  }
  public void addBook(Book book) {
      books.add(book);
  }
  public List<Book> searchByTitle(String title) {
      List<Book> result = new ArrayList<>();
      for (Book book : books) {
          if (book.getTitle().equalsIgnoreCase(title)) {
              result.add(book);
          }
      }
      return result;
  }

  public List<Book> searchByAuthor(String author) {
      List<Book> result = new ArrayList<>();
      for (Book book : books) {
          if (book.getAuthor().equalsIgnoreCase(author)) {
              result.add(book);
          }
      }
      return result;
  }

  public void displayAllBooks() {
      System.out.println("Книги в библиотеке ");
      for (Book book : books) {
          System.out.println(book);
      }
  }

  public List<Book> getStatusBooks() {
      List<Book> statusBooks = new ArrayList<>();
      for (Book book : books) {
          if (book.getStatus()){
              statusBooks.add(book);
          }
      }
      return statusBooks;
  }

}


package t1;

public class Librarian {
    private String name;

    public Librarian(String name) {
        this.name = name;
    }
    public void manageLibrary(Library library) {
        System.out.println(name + "управляет библиотекой");
    }
}


package t1;

import java.util.ArrayList;
import java.util.List;

public class Reader {
    private String name;
    private List<Book> rentedBooks;
    private static final int MAX_RENTAL_LIMIT = 5;

    public Reader(String name) {
        this.name = name;
        this.rentedBooks = new ArrayList<Book>();
    }
    public boolean rentBook(Book book) {
        if(rentedBooks.size() >= MAX_RENTAL_LIMIT) {
            System.out.println("Максимум "+MAX_RENTAL_LIMIT+" в руки");
            return false;
        }
        if (!book.getStatus()){
            System.out.println(book.getTitle()+" нет в наличии");
            return false;
        }
        rentedBooks.add(book);
        book.setStatus(false);
        System.out.println(book.getTitle()+" арендована");
        return true;
    }

    public void returnBook(Book book) {
        if(rentedBooks.remove(book)) {
            book.setStatus(true);
            System.out.println(name+ "вернул книгу"+ book.getTitle());
        }
        else {
            System.out.println("Книга" + book.getTitle()+" не возвращена");
        }
    }
     public List<Book> getRentedBooks() {
        return rentedBooks;
     }
}

