package com.company;

public class Main {

    public static void main(String[] args) {
        Library library = new Library();
        Book book1 = new Book("Джо-Джо", "Хирохико Араки", "123-456", 3);
        Book book2 = new Book("Наруто", "Масаши Кишимото", "789-101", 2);

        library.addBook(book1);
        library.addBook(book2);

        Reader reader1 = new Reader("Мурат Бакытжан", "R_001");
        Reader reader2 = new Reader("Темирбекова Зухра", "R_002");

        library.registerReader(reader1);
        library.registerReader(reader2);

        library.issueBook("123-456", "R_001");
        library.issueBook("123-456", "R_002");
        library.issueBook("123-456", "R_002");

        library.returnBook("123-456");

        library.removeBook("789-101");
        library.removeReader("R_002");

        library.showBooks();
        library.showReaders();
    }
}
package com.company;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private int copies;

    public Book(String title, String author, String isbn, int copies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.copies = copies;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    @Override
    public String toString() {
        return "Book: " +
                "title: " + title + '\n' +
                "author: " + author + '\n' +
                "isbn=: " + isbn + '\n' +
                "copies: " + copies + '\n' +
                "========================";
    }
}
package com.company;

public class Reader {
    private String name;
    private String readerId;

    public Reader(String name, String readerId) {
        this.name = name;
        this.readerId = readerId;
    }

    public String getName() {
        return name;
    }

    public String getReaderId() {
        return readerId;
    }

    @Override
    public String toString() {
        return "Reader: " +
                "name: " + name + '\n' +
                "readerId: " + readerId + '\n' +
                "===============================";
    }
}
package com.company;

import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books;
    private ArrayList<Reader> readers;

    public Library() {
        books = new ArrayList<>();
        readers = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Книга добавлена: " + book);
    }

    public void removeBook(String isbn) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn)) {
                System.out.println("Книга удалена: " + books.get(i));
                books.remove(i);
                return;
            }
        }
        System.out.println("Книга не найдена.");
    }

    public void registerReader(Reader reader) {
        readers.add(reader);
        System.out.println("Читатель зарегистрирован: " + reader);
    }

    public void removeReader(String readerId) {
        for (int i = 0; i < readers.size(); i++) {
            if (readers.get(i).getReaderId().equals(readerId)) {
                System.out.println("Читатель удален: " + readers.get(i));
                readers.remove(i);
                return;
            }
        }
        System.out.println("Читатель не найден.");
    }


    public void issueBook(String isbn, String readerId) {
        Book book = findBook(isbn);
        if (book == null) {
            System.out.println("Книга не найдена.");
            return;
        }

        if (book.getCopies() > 0) {
            Reader reader = findReader(readerId);
            if (reader != null) {
                book.setCopies(book.getCopies() - 1);
                System.out.println("Книга выдана: " + book.getTitle() + " читателю " + reader.getName());
            } else {
                System.out.println("Читатель с ID " + readerId + " не найден.");
            }
        } else {
            System.out.println("Нет доступных копий книги: " + book.getTitle());
        }
    }

    public void returnBook(String isbn) {
        Book book = findBook(isbn);
        if (book != null) {
            book.setCopies(book.getCopies() + 1);
            System.out.println("Книга возвращена: " + book.getTitle());
        } else {
            System.out.println("Книга с не найдена.");
        }
    }

    private Book findBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }


    private Reader findReader(String readerId) {
        for (Reader reader : readers) {
            if (reader.getReaderId().equals(readerId)) {
                return reader;
            }
        }
        return null;
    }


    public void showBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }


    public void showReaders() {
        for (Reader reader : readers) {
            System.out.println(reader);
        }
    }
}
