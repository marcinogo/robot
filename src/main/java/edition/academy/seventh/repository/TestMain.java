package edition.academy.seventh.repository;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static edition.academy.seventh.database.connector.DatabaseTypes.H2;

/** @author Agnieszka Trzewik */
public class TestMain {

  static ConnectorProvider connectorProvider = ConnectorFactory.of(H2);
  static EntityManager entityManager = connectorProvider.getEntityManager();

  public static void main(String[] args) {
    BookDto bookDto =
        new BookDto(
            "Tytul", "", "autor", "realCeba", "promoCena", "Link do obrazka", "href", "Empik");
    BookId bookId = new BookId(bookDto.getTitle(), bookDto.getAuthors());
    Book book = new Book(bookId, bookDto.getSubtitle());

    Bookstore bookstore = new Bookstore(bookDto.getBookstore());

    BookstoreBook bookstoreBook =
        new BookstoreBook(bookDto.getHref(), bookDto.getImageLink(), bookstore, book);

    BigDecimal retailPrice = new BigDecimal(bookDto.getRetailPrice().split(" ")[0]);
    BigDecimal promotionalPrice = new BigDecimal(bookDto.getPromotionalPrice().split(" ")[0]);
    String currency = bookDto.getPromotionalPrice().split(" ")[1];

    PriceHistory priceHistory =
        new PriceHistory(
            bookstoreBook, retailPrice, promotionalPrice, currency, LocalDateTime.now());

    bookstoreBook.getPriceHistories().add(priceHistory);

    BookDto bookDto2 =
        new BookDto(
            "Tytul", "", "autor", "realCeba", "promoCena", "Link do obrazka", "href", "Empik");
    BookId bookId2 = new BookId(bookDto.getTitle(), bookDto.getAuthors());
    Book book2 = new Book(bookId, bookDto.getSubtitle());

    Bookstore bookstore2 = new Bookstore(bookDto.getBookstore());

    BookstoreBook bookstoreBook2 = new BookstoreBook(bookDto.getHref(), "fff", bookstore, book);

    BigDecimal retailPrice2 = new BigDecimal(bookDto2.getRetailPrice().split(" ")[0]);
    BigDecimal promotionalPrice2 = new BigDecimal(bookDto2.getPromotionalPrice().split(" ")[0]);
    String currency2 = bookDto2.getPromotionalPrice().split(" ")[1];

    PriceHistory priceHistory2 =
        new PriceHistory(
            bookstoreBook,
            retailPrice2,
            promotionalPrice2,
            currency2,
            LocalDateTime.now());

    bookstoreBook.getPriceHistories().add(priceHistory);

    entityManager = connectorProvider.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();

    entityManager.persist(book);
    entityManager.persist(bookstore);
    entityManager.persist(bookstoreBook);

    Book book1 = entityManager.find(Book.class, book2.getBookId());
    book.setBookId(book2.getBookId());
    entityManager.refresh(book);
    Bookstore bookstore1 = entityManager.find(Bookstore.class, bookstore2.getName());
    bookstore.setName(bookstore2.getName());
    entityManager.refresh(bookstore);
    BookstoreBook bookstoreBook1 =
        entityManager.find(BookstoreBook.class, bookstoreBook2.getHyperlink());
    if (bookstoreBook1 != null) {
      bookstoreBook2.setHyperlink(bookstoreBook1.getHyperlink());
      entityManager.merge(bookstoreBook2);

    } else entityManager.persist(bookstoreBook2);

    transaction.commit();
    entityManager.close();

    connectorProvider.getEntityManagerFactory().close();

    //    entityManager = connectorProvider.getEntityManager();
    //
    //
    //    BookstoreBook bookstoreBook =
    //
    // findBookstoreBookById("https://ksiegarnia.pwn.pl/Ilustrowany-slownik-ortograficzny,781898502,p.html");
    //
    //    bookstoreBook.getPriceHistories().forEach(p -> System.out.println(p.getDate()));
    //
    //    entityManager.close();
    //    connectorProvider.getEntityManagerFactory().close();
  }

  private static BookstoreBook findBookstoreBookById(String href) {
    return entityManager.find(BookstoreBook.class, href);
  }

  // stworzyc tytul autor, wszytskie pricehistory
}
