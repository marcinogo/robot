package edition.academy.seventh.persistence;

import edition.academy.seventh.connector.ConnectorFactory;
import edition.academy.seventh.connector.ConnectorProvider;
import edition.academy.seventh.connector.DatabaseType;
import edition.academy.seventh.persistence.model.Book;
import edition.academy.seventh.persistence.model.BookId;
import edition.academy.seventh.persistence.model.Bookstore;
import edition.academy.seventh.persistence.model.BookstoreBook;
import edition.academy.seventh.persistence.response.BookDto;
import edition.academy.seventh.persistence.response.BookstoreBookDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static edition.academy.seventh.persistence.ModelParserIntoBookDtos.parseBookstoreBooksIntoBookDtos;

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
  private BookDtoParser bookDtoParser;
  private boolean shouldCloseConnectorProvider;

  @Autowired
  public BookRepository(BookDtoParser bookDtoParser, @Value("${robot.db}") String database) {
    connectorProvider = ConnectorFactory.of(DatabaseType.valueOf(database));
    this.bookDtoParser = bookDtoParser;
    shouldCloseConnectorProvider = checkIfConnectorProviderShouldBeClosed(database);
  }

  public BookstoreBook getBookstoreBookById(Long id) {
    return entityManager.find(BookstoreBook.class, id);
  }

  /**
   * Adds books records to the database.
   *
   * @param bookDtos {@code List<BookDto>} to be added
   */
  void addBooksToDatabase(List<BookDto> bookDtos) {
    entityManager = connectorProvider.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    bookDtos.forEach(this::addBookToDatabase);
    logger.info("Saving " + bookDtos.size() + " books in database");
    transaction.commit();
    entityManager.close();
    if (shouldCloseConnectorProvider) connectorProvider.close();
  }

  /**
   * Retrieves all books with latest price information from database.
   *
   * @return {@code List<BookDto>}
   */
  List<BookDto> getLatestBooksFromDatabase() {

    entityManager = connectorProvider.getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<BookstoreBook> query = criteriaBuilder.createQuery(BookstoreBook.class);

    Root<BookstoreBook> from = query.from(BookstoreBook.class);
    query.select(from);
    List<BookstoreBook> bookstoreBookList = entityManager.createQuery(query).getResultList();

    logger.info("Called getBooksFromDatabase(), returning " + bookstoreBookList.size() + " books");
    entityManager.close();
    if (shouldCloseConnectorProvider) connectorProvider.close();

    return parseBookstoreBooksIntoBookDtos(bookstoreBookList);
  }

  /**
   * Retrieves specific {@link BookstoreBook} from the database based on the book's hyperlink. If
   * there href does not exist, then it return null.
   *
   * @param href link of the searched book.
   * @return {@link BookstoreBook} found by id, or null if href does not exist.
   */
  BookstoreBookDto getBookstoreBookDtoByHref(String href) {

    entityManager = connectorProvider.getEntityManager();

    BookstoreBook bookstoreBook = entityManager.find(BookstoreBook.class, href);

    if (bookstoreBook == null) {
      logger.info("Cannot find book with href " + href);
      return null;
    }

    logger.info("Called getBookstoreBookDtoById()");

    entityManager.close();

    return bookDtoParser.parseBookstoreBookIntoBookstoreBookDto(bookstoreBook);
  }

  BookstoreBookDto getBookstoreBookDtoById(Long id) {

    entityManager = connectorProvider.getEntityManager();

    BookstoreBook bookstoreBook = entityManager.find(BookstoreBook.class, id);

    if (bookstoreBook == null) {
      logger.info("Cannot find book with id " + id);
      return null;
    }

    logger.info("Called getBookstoreBookDtoById()");

    entityManager.close();

    return bookDtoParser.parseBookstoreBookIntoBookstoreBookDto(bookstoreBook);
  }

  Book getBookById(BookId bookId) {
    return entityManager.find(Book.class, bookId);
  }

  Bookstore getBookstoreById(String bookstoreId) {
    return entityManager.find(Bookstore.class, bookstoreId);
  }

  BookstoreBook getBookstoreBookByTitleAuthorBookstore(
      String title, String author, String bookstoreName) {
    title = title.replace('\'', ' ');
    author = author.replace('\'', ' ');

    String hql =
        "FROM edition.academy.seventh.persistence.model.BookstoreBook WHERE book.bookId.title = \'"
            + title
            + "\' and book.bookId.author = \'"
            + author
            + "\' and bookstore.name = \'"
            + bookstoreName
            + "'";
    List<BookstoreBook> resultList =
        (List<BookstoreBook>) entityManager.createQuery(hql).getResultList();

    return resultList.isEmpty() ? null : resultList.get(0);
  }

  void setConnectorProvider(ConnectorProvider connectorProvider) {
    this.connectorProvider = connectorProvider;
  }

  /**
   * Updates new {@link BookstoreBook} with existing values from database, or persist whole entity.
   *
   * @param bookstoreBook new to save
   * @param bookstoreBookAlreadyInDatabase existing in database
   */
  void saveOrUpdateBookstoreBook(
      BookstoreBook bookstoreBook, BookstoreBook bookstoreBookAlreadyInDatabase) {
    if (bookstoreBookAlreadyInDatabase != null) {
      bookstoreBook.setId(bookstoreBookAlreadyInDatabase.getId());
      entityManager.merge(bookstoreBook);
    } else {
      entityManager.persist(entityManager.merge(bookstoreBook));
    }
  }

  /**
   * Updates new {@link Bookstore} with existing values from database, or persist whole entity.
   *
   * @param bookstore new to save
   * @param bookstoreAlreadyInDatabase existing in database
   */
  void saveOrUpdateBookstore(Bookstore bookstore, Bookstore bookstoreAlreadyInDatabase) {
    if (bookstoreAlreadyInDatabase != null) {
      bookstore.setName(bookstoreAlreadyInDatabase.getName());
      entityManager.merge(bookstore);
    } else {
      entityManager.persist(bookstore);
    }
  }

  /**
   * Updates new {@link Book} with existing values from database, or persist whole entity.
   *
   * @param book new to save
   * @param bookAlreadyInDatabase existing in database
   */
  void saveOrUpdateBook(Book book, Book bookAlreadyInDatabase) {
    if (bookAlreadyInDatabase != null) {
      book.setBookId(bookAlreadyInDatabase.getBookId());
      entityManager.merge(book);
    } else {
      entityManager.persist(book);
    }
  }

  private void addBookToDatabase(BookDto bookDto) {
    bookDtoParser.parseBookDtoIntoModel(bookDto);
  }

  private boolean checkIfConnectorProviderShouldBeClosed(String database) {
    return database.equalsIgnoreCase(DatabaseType.POSTGRESQL.name());
  }
}
