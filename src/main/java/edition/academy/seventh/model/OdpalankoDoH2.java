package edition.academy.seventh.model;

import edition.academy.seventh.database.model.DTBook;
import edition.academy.seventh.repository.BookRepository;

import java.time.LocalDate;
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

    BookstoreBook bookstoreBook = bookParser(book);
    BookstoreBook bookstoreBook1 = bookParser(book1);
    BookstoreBook bookstoreBook2 = bookParser(book2);
    BookstoreBook bookstoreBook3 = bookParser(book3);

    List<BookstoreBook> books = new ArrayList<>();

    books.add(bookstoreBook);
    books.add(bookstoreBook2);
    books.add(bookstoreBook1);
    books.add(bookstoreBook3);

    new BookRepository().addBooksToDataBase(books);
  }

  private static BookstoreBook bookParser(DTBook book) {
    Book book2 = new Book(book.getSubtitle(), new BookId(book.getTitle(), book.getAuthors()));
    Bookstore bookstore = new Bookstore(book.getBookstore());
    HrefAndImage hrefAndImage = new HrefAndImage(book.getHref(), book.getImage());

    return new BookstoreBook(
        bookstore,
        book2,
        Integer.parseInt(book.getPrice()),
        Integer.parseInt(book.getPromoPrice()),
        LocalDate.now(),
        hrefAndImage);
  }
}
