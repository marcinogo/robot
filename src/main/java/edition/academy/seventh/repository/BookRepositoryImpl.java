package edition.academy.seventh.repository;

import static edition.academy.seventh.database.connector.DatabaseTypes.POSTGRESQL;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.database.model.BookstoreBookDto;
import edition.academy.seventh.model.BookstoreBook;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Allows to persists and retrieve data about books from the database. This information is
 * transferred through the application as {@link BookDto}.
 *
 * @author Agnieszka Trzewik
 */
@Repository
public class BookRepositoryImpl implements BookRepository {
  private static final Logger logger = LoggerFactory.getLogger(BookRepositoryImpl.class);
  private EntityManager entityManager;
  private ConnectorProvider connectorProvider;
  private BookDtoParser bookDtoParser;
  private ModelParserIntoBookDtos modelParserIntoBookDtos;
  private BookstoreBookParserIntoBookstoreBookDto bookstoreBookParserIntoBookstoreBookDto;

  @Autowired
  public BookRepositoryImpl(
      BookDtoParser bookDtoParser,
      ModelParserIntoBookDtos modelParserIntoBookDtos,
      BookstoreBookParserIntoBookstoreBookDto bookstoreBookParserIntoBookstoreBookDto) {
    connectorProvider = ConnectorFactory.of(POSTGRESQL);
    this.bookDtoParser = bookDtoParser;
    this.modelParserIntoBookDtos = modelParserIntoBookDtos;
    this.bookstoreBookParserIntoBookstoreBookDto = bookstoreBookParserIntoBookstoreBookDto;
  }

  /**
   * Adds books records to the database.
   *
   * @param bookDtos {@code List<BookDto>} to be added
   */
  @Override
  public void addBooksToDatabase(List<BookDto> bookDtos) {
    entityManager = connectorProvider.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    bookDtos.forEach(this::addBookToDatabase);
    logger.info("Saving " + bookDtos.size() + " books in database");
    transaction.commit();
    entityManager.close();
    connectorProvider.getEntityManager().close();
  }

  /**
   * Retrieves all books with latest price information from database.
   *
   * @return {@code List<BookDto>}
   */
  @Override
  public List<BookDto> getLatestBooksFromDatabase() {

    entityManager = connectorProvider.getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<BookstoreBook> query = criteriaBuilder.createQuery(BookstoreBook.class);

    Root<BookstoreBook> from = query.from(BookstoreBook.class);
    query.select(from);
    List<BookstoreBook> bookstoreBookList = entityManager.createQuery(query).getResultList();

    logger.info("Called getBooksFromDatabase(), returning " + bookstoreBookList.size() + " books");

    entityManager.close();
    return modelParserIntoBookDtos.parseBookstoreBooksIntoBookDtos(bookstoreBookList);
  }

  /**
   * Retrieves specific {@link BookstoreBook} from the database based on the book's hyperlink.
   * If there href does not exist, then it return null.
   *
   * @param href link of the searched book.
   * @return {@link BookstoreBook} found by id, or null if href does not exist.
   */
  @Override
  public BookstoreBookDto getBookstoreBookDtoByHref(String href) {

    entityManager = connectorProvider.getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<BookstoreBook> query = criteriaBuilder.createQuery(BookstoreBook.class);

    Root<BookstoreBook> from = query.from(BookstoreBook.class);
    Path<Object> hyperLink = from.get("hyperlink");
    query.select(from).where(criteriaBuilder.equal(hyperLink, href));
    BookstoreBook bookstoreBook;
    try {
      bookstoreBook = entityManager.createQuery(query).getSingleResult();
    } catch (NoResultException exception) {
      logger.info("Cannot find book with href " + href);
      return null;
    }

    logger.info("Called getBookstoreBookDtoByHref()");

    entityManager.close();

    return bookstoreBookParserIntoBookstoreBookDto.parseBookstoreBookIntoBookstoreBookDto(
        bookstoreBook);
  }

  private void addBookToDatabase(BookDto bookDto) {
    bookDtoParser.parseBookDtoIntoModel(bookDto, entityManager);
  }

  public void setConnectorProvider(
      ConnectorProvider connectorProvider) {
    this.connectorProvider = connectorProvider;
  }
}
