package edition.academy.seventh.model;

import edition.academy.seventh.database.model.DTBook;
import edition.academy.seventh.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class OdpalankoDoH2 {

  public static void main(String[] args) {
    DTBook book =
        new DTBook("Title", "Substitile", "Author", "Bookstore", "50", "40", "http", "http2");
    DTBook book1 =
        new DTBook("Title2", "Substitile2", "Author2", "Bookstore", "510", "400", "http", "http2");
    DTBook book2 =
        new DTBook("Title2", "Substitile2", "Author2", "Bookstore", "540", "400", "http", "http2");
    DTBook book3 =
        new DTBook("Title2", "Substitile2", "Author2", "Bookstore", "520", "400", "http", "http2");

    List<DTBook> books = new ArrayList<>();

    books.add(book);
    books.add(book1);
    books.add(book2);
    books.add(book3);

    BookRepository bookRepository = new BookRepository();
    bookRepository.addBooksToDatabase(books);

    bookRepository.getBooksFromDatabase().forEach(b -> System.out.println(b.getTitle()));
  }
}
