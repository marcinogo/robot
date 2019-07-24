package edition.academy.seventh.repository;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.*;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import static edition.academy.seventh.database.connector.DatabaseTypes.H2;
import static edition.academy.seventh.database.connector.DatabaseTypes.POSTGRESQL;
import static edition.academy.seventh.repository.BookParser.parseBookstoreBookListIntoDTBookList;
import static edition.academy.seventh.repository.BookParser.parseDTBookIntoModel;

/**
 * Allows to persists and retrieve data about books from the database. This information is
 * transferred through the application as {@link BookDto}.
 *
 * @author Agnieszka Trzewik
 */
@Repository
public class BookRepository {
  private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
  private EntityManager entityManager;
  private ConnectorProvider connectorProvider;

  public BookRepository() {
    connectorProvider = ConnectorFactory.of(H2);
  }

  /**
   * Adds books records to the database.
   *
   * @param bookDtos {@code List<BookDto>} to be added
   */
  public void addBooksToDatabase(List<BookDto> bookDtos) {
    entityManager = connectorProvider.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    bookDtos.forEach(this::addBookToDatabase);
    logger.info("Saving " + bookDtos.size() + " books in database");
    transaction.commit();
    entityManager.close();
    connectorProvider.getEntityManagerFactory().close();
  }

  /**
   * Retrieves all books from database.
   *
   * @return {@code List<BookDto>}
   */
  public List<BookDto> getLatestBooksFromDatabase() {

    entityManager = connectorProvider.getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<BookstoreBook> query = criteriaBuilder.createQuery(BookstoreBook.class);

    Root<BookstoreBook> from = query.from(BookstoreBook.class);
    query.select(from);
    List<BookstoreBook> bookstoreBookList = entityManager.createQuery(query).getResultList();

    logger.info("Called getBooksFromDatebase(), returning " + bookstoreBookList.size() + " books");
    List<BookDto> booksFromDataBase = parseBookstoreBookListIntoDTBookList(bookstoreBookList);

    entityManager.close();
    return booksFromDataBase;
  }

  private void addBookToDatabase(BookDto bookDto) {

    BookId bookId = new BookId(bookDto.getTitle(), bookDto.getAuthors());
    Book book = new Book(bookDto.getSubtitle(), bookId);
    UrlResources urlResources = new UrlResources(bookDto.getHref(), bookDto.getImageLink());
    Bookstore bookstore = new Bookstore(bookDto.getBookstore());

    BookstoreBookId bookstoreBookId = new BookstoreBookId(bookstore, book);
    BookstoreBook bookstoreBook = new BookstoreBook(bookstoreBookId, urlResources);

    PriceHistory priceHistory =
            new PriceHistory(
                    bookstoreBook,
                    bookDto.getRetailPrice(),
                    bookDto.getPromotionalPrice(),
                    LocalDateTime.now());

    Bookstore previousBookstore =
        entityManager.find(
            Bookstore.class, bookstoreBook.getBookstoreBookId().getBookstore().getName());
    Book previousBook =
        entityManager.find(Book.class, bookstoreBook.getBookstoreBookId().getBook().getBookId());
    UrlResources previousUrlResources =
        entityManager.find(UrlResources.class, bookstoreBook.getUrlResources().getHyperLink());
    BookstoreBook previousBookstoreBook =
        entityManager.find(BookstoreBook.class, bookstoreBook.getBookstoreBookId());

    refreshVariablesFromBookstoreBook(
        bookstore,
        previousBookstore,
        book,
        previousBook,
        urlResources,
        previousUrlResources,
        bookstoreBook,
        previousBookstoreBook,
        priceHistory);
  }

  private void refreshVariablesFromBookstoreBook(
      Bookstore bookstore,
      Bookstore previousBookstore,
      Book book,
      Book previousBook,
      UrlResources urlResources,
      UrlResources previousUrlResources,
      BookstoreBook bookstoreBook,
      BookstoreBook previousBookstoreBook,
      PriceHistory priceHistory) {

    if (previousBook != null) {
      book.setBookId(previousBook.getBookId());
      entityManager.refresh(entityManager.merge(book));

    } else entityManager.persist(book);
    if (previousUrlResources != null) {
      urlResources.setHyperLink(previousUrlResources.getHyperLink());
      entityManager.refresh(entityManager.merge(urlResources));

    } else entityManager.persist(urlResources);
    if (previousBookstore != null) {
      bookstore.setName(previousBookstore.getName());
      entityManager.refresh(entityManager.merge(previousBookstore));
    } else entityManager.persist(bookstore);

    if (previousBookstoreBook != null) {
      bookstoreBook.setBookstoreBookId(previousBookstoreBook.getBookstoreBookId());

      bookstoreBook.getPriceHistories().add(priceHistory);
      entityManager.refresh(entityManager.merge(bookstoreBook));
    } else {
      bookstoreBook.getPriceHistories().add(priceHistory);
      entityManager.persist(bookstoreBook);
    }
  }
}
