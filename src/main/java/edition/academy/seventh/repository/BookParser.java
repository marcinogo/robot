package edition.academy.seventh.repository;

import edition.academy.seventh.database.model.BookDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import edition.academy.seventh.model.*;
import org.springframework.stereotype.Service;


/**
 * Parses {@link BookDto} into {@link BookstoreBook} and {@code List<BookstoreBook>} into {@code
 * List<BookDto>}.
 *
 * @author Agnieszka Trzewik
 */
@Service
class BookParser {

  void parseBookDtoIntoModel(BookDto bookDto, EntityManager entityManager) {
    Book book = createBook(bookDto);
    Bookstore bookstore = createBookstore(bookDto);
    BookstoreBook bookstoreBook = createBookstoreBook(bookDto, book, bookstore);
    PriceHistory priceHistory = createPriceHistory(bookDto, bookstoreBook);

    Book bookAlreadyInDatabase =
            entityManager.find(Book.class, bookstoreBook.getBook().getBookId());
    Bookstore bookstoreAlreadyInDatabase =
        entityManager.find(Bookstore.class, bookstoreBook.getBookstore().getName());
    BookstoreBook bookstoreBookAlreadyInDatabase =
        entityManager.find(BookstoreBook.class, bookstoreBook.getHyperLink());

    saveOrUpdateModel(
        entityManager,
        bookstore,
        bookstoreAlreadyInDatabase,
        book,
        bookAlreadyInDatabase,
        bookstoreBook,
        bookstoreBookAlreadyInDatabase,
        priceHistory);
  }

  private PriceHistory createPriceHistory(BookDto bookDto, BookstoreBook bookstoreBook) {
    return new PriceHistory(
        bookstoreBook,
        bookDto.getRetailPrice(),
        bookDto.getPromotionalPrice(),
        LocalDateTime.now());
  }

  private BookstoreBook createBookstoreBook(BookDto bookDto, Book book, Bookstore bookstore) {
    return new BookstoreBook(bookDto.getHref(), bookDto.getImageLink(), bookstore, book);
  }

  private Bookstore createBookstore(BookDto bookDto) {
    return new Bookstore(bookDto.getBookstore());
  }

  private Book createBook(BookDto bookDto) {
    BookId bookId = new BookId(bookDto.getTitle(), bookDto.getAuthors());
    return new Book(bookDto.getSubtitle(), bookId);
  }

  private void saveOrUpdateModel(
      EntityManager entityManager,
      Bookstore bookstore,
      Bookstore previousBookstore,
      Book book,
      Book previousBook,
      BookstoreBook bookstoreBook,
      BookstoreBook previousBookstoreBook,
      PriceHistory priceHistory) {

    saveOrUpdateBook(entityManager, book, previousBook);
    saveOrUpdateBookstore(entityManager, bookstore, previousBookstore);
    saveOrUpdateBookstoreBook(entityManager, bookstoreBook, previousBookstoreBook, priceHistory);
  }

  private void saveOrUpdateBookstoreBook(
      EntityManager entityManager,
      BookstoreBook bookstoreBook,
      BookstoreBook bookstoreBookAlreadyInDatabase,
      PriceHistory priceHistory) {
    bookstoreBook.getPriceHistories().add(priceHistory);
    if (bookstoreBookAlreadyInDatabase != null) {
      bookstoreBook.setHyperLink(bookstoreBookAlreadyInDatabase.getHyperLink());
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

  private void saveOrUpdateBook(EntityManager entityManager, Book book, Book bookAlreadyInDatabase) {
    if (bookAlreadyInDatabase != null) {
      book.setBookId(bookAlreadyInDatabase.getBookId());
      entityManager.merge(book);

    } else entityManager.persist(book);
  }

  List<BookDto> parseBookstoreBookListIntoDTBookList(List<BookstoreBook> bookstoreBooks) {
    return bookstoreBooks.stream()
        .map(
            bookstoreBook ->
                new BookDto(
                    bookstoreBook.getBook().getBookId().getTitle(),
                    bookstoreBook.getBook().getSubtitle(),
                    bookstoreBook.getBook().getBookId().getAuthor(),
                    bookstoreBook.getLastElementOfPriceHistories().getRetailPrice(),
                    bookstoreBook.getLastElementOfPriceHistories().getPromotionalPrice(),
                    bookstoreBook.getImageLink(),
                    bookstoreBook.getHyperLink(),
                    bookstoreBook.getBookstore().getName()))
        .collect(Collectors.toList());
  }
}
