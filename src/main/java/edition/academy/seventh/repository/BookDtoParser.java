package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Parses {@link BookDto} into database model and save or update changes.
 *
 * @author Agnieszka Trzewik
 */
@Service
class BookDtoParser {

  /**
   * Creates all required records. Tries to find specific ones in a database,
   * if they do exist updates them, otherwise creates new ones.
   *
   * @param bookDto to be parsed into model.
   * @param entityManager to perform required actions.
   */
  void parseBookDtoIntoModel(BookDto bookDto, EntityManager entityManager) {
    Book book = createBook(bookDto);
    Bookstore bookstore = createBookstore(bookDto);
    BookstoreBook bookstoreBook = createBookstoreBook(bookDto, book, bookstore);
    PriceAtTheMoment priceAtTheMoment = createPriceHistory(bookDto, bookstoreBook);

    Book bookAlreadyInDatabase =
        entityManager.find(Book.class, bookstoreBook.getBook().getBookId());
    Bookstore bookstoreAlreadyInDatabase =
        entityManager.find(Bookstore.class, bookstoreBook.getBookstore().getName());
    BookstoreBook bookstoreBookAlreadyInDatabase =
        entityManager.find(BookstoreBook.class, bookstoreBook.getHyperlink());

    saveOrUpdateModel(
        entityManager,
        bookstore,
        bookstoreAlreadyInDatabase,
        book,
        bookAlreadyInDatabase,
        bookstoreBook,
        bookstoreBookAlreadyInDatabase,
        priceAtTheMoment);
  }

  private PriceAtTheMoment createPriceHistory(BookDto bookDto, BookstoreBook bookstoreBook) {

    String currency = findCurrency(String.valueOf(bookDto.getRetailPrice()));
    BigDecimal retailPrice =
        establishRetailPrice(bookDto.getRetailPrice(), bookDto.getPromotionalPrice());
    BigDecimal promotionalPrice =
        establishPromotionalPrice(retailPrice, bookDto.getPromotionalPrice());

    return new PriceAtTheMoment(
        bookstoreBook, retailPrice, promotionalPrice, currency, LocalDateTime.now());
  }

  private String findCurrency(String price) {
    if (price.contains("zł")) {
      return "zł";
    } else {
      return "$";
    }
  }

  private BigDecimal establishPromotionalPrice(BigDecimal retailPrice, String promotionalPriceDto) {
    return promotionalPriceDto.isEmpty()
        ? retailPrice
        : parseStringPriceIntoBigDecimal(promotionalPriceDto);
  }

  private BigDecimal establishRetailPrice(String retailPriceDto, String promotionalPriceDto) {
    return retailPriceDto.isEmpty()
        ? establishPromotionalPrice(null, promotionalPriceDto)
        : parseStringPriceIntoBigDecimal(retailPriceDto);
  }

  private BigDecimal parseStringPriceIntoBigDecimal(String price) {
    price = price.replace(",", ".");
    price = price.replaceAll("[^0-9.]", "");
    return new BigDecimal(price);
  }

  private BookstoreBook createBookstoreBook(BookDto bookDto, Book book, Bookstore bookstore) {
    return new BookstoreBook(bookDto.getHref(), bookDto.getImageLink(), bookstore, book);
  }

  private Bookstore createBookstore(BookDto bookDto) {
    return new Bookstore(bookDto.getBookstore());
  }

  private Book createBook(BookDto bookDto) {
    BookId bookId = new BookId(bookDto.getTitle(), bookDto.getAuthors());
    return new Book(bookId, bookDto.getSubtitle());
  }

  private void saveOrUpdateModel(
      EntityManager entityManager,
      Bookstore bookstore,
      Bookstore previousBookstore,
      Book book,
      Book previousBook,
      BookstoreBook bookstoreBook,
      BookstoreBook previousBookstoreBook,
      PriceAtTheMoment priceAtTheMoment) {

    saveOrUpdateBook(entityManager, book, previousBook);
    saveOrUpdateBookstore(entityManager, bookstore, previousBookstore);
    saveOrUpdateBookstoreBook(entityManager, bookstoreBook, previousBookstoreBook,
        priceAtTheMoment);
  }

  private void saveOrUpdateBookstoreBook(
      EntityManager entityManager,
      BookstoreBook bookstoreBook,
      BookstoreBook bookstoreBookAlreadyInDatabase,
      PriceAtTheMoment priceAtTheMoment) {
    bookstoreBook.getPriceHistories().add(priceAtTheMoment);
    if (bookstoreBookAlreadyInDatabase != null) {
      bookstoreBook.setHyperlink(bookstoreBookAlreadyInDatabase.getHyperlink());
      entityManager.merge(bookstoreBook);
    } else {
      entityManager.persist(entityManager.merge(bookstoreBook));
    }
  }

  private void saveOrUpdateBookstore(
      EntityManager entityManager, Bookstore bookstore, Bookstore bookstoreAlreadyInDatabase) {
    if (bookstoreAlreadyInDatabase != null) {
      bookstore.setName(bookstoreAlreadyInDatabase.getName());
      entityManager.merge(bookstore);
    } else entityManager.persist(bookstore);
  }

  private void saveOrUpdateBook(
      EntityManager entityManager, Book book, Book bookAlreadyInDatabase) {
    if (bookAlreadyInDatabase != null) {
      book.setBookId(bookAlreadyInDatabase.getBookId());
      entityManager.merge(book);

    } else entityManager.persist(book);
  }
}
