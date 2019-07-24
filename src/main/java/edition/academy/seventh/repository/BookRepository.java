package edition.academy.seventh.repository;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.BookstoreBook;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import static edition.academy.seventh.database.connector.DatabaseTypes.H2;

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
  private BookParser bookParser;

  @Autowired
  public BookRepository(BookParser bookParser) {
    connectorProvider = ConnectorFactory.of(H2);
    this.bookParser = bookParser;
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

    entityManager.close();
    return bookParser.parseBookstoreBookListIntoDTBookList(bookstoreBookList);
  }

  private void addBookToDatabase(BookDto bookDto) {
    bookParser.parseBookDtoIntoModel(bookDto, entityManager);
  }
}
