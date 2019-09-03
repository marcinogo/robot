package edition.academy.seventh.pagination;

import edition.academy.seventh.connector.ConnectorFactory;
import edition.academy.seventh.connector.ConnectorProvider;
import edition.academy.seventh.connector.DatabaseType;
import edition.academy.seventh.persistence.model.BookstoreBook;
import edition.academy.seventh.persistence.response.BookDto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static edition.academy.seventh.persistence.ModelParserIntoBookDtos.parseBookstoreBooksIntoBookDtos;

/** @author Agnieszka Trzewik */
@Repository
public class PaginationRepository implements Observer {

  private static final Logger LOGGER = LoggerFactory.getLogger(PaginationRepository.class);
  private int currentPageNumber = 1;
  private ConnectorProvider connectorProvider;
  private EntityManager entityManager;
  private PaginationResult paginationResult;

  @Autowired
  public PaginationRepository(@Value("${robot.db}") String database) {
    connectorProvider = ConnectorFactory.of(DatabaseType.valueOf(database));
    paginationResult = createPaginationResult();
  }

  @Override
  public void update(PaginationResult paginationResult) {
    this.paginationResult = paginationResult;
  }

  public PaginationResult createPaginationResult() {
    try {

      entityManager = connectorProvider.getEntityManager();
      Session session = entityManager.unwrap(Session.class);
      String sql = "FROM " + BookstoreBook.class.getName();
      Query<BookstoreBook> query = session.createQuery(sql, BookstoreBook.class);

      return new PaginationResult(query);
    } finally {
      entityManager.close();
      connectorProvider.close();
    }
  }

  List<BookDto> getCurrentPage() {
    LOGGER.info("Current page: " + currentPageNumber);
    return getPaginationResult(currentPageNumber);
  }

  List<BookDto> getNextPage() {
    List<BookDto> paginationResult = getPaginationResult(currentPageNumber + 1);
    if (!paginationResult.isEmpty()) {
      currentPageNumber++;
      LOGGER.info("Current page: " + currentPageNumber);
      return paginationResult;
    }
    LOGGER.info("It is a last page:  " + currentPageNumber);
    return getCurrentPage();
  }

  List<BookDto> getPreviousPage() {
    List<BookDto> paginationResult = getPaginationResult(currentPageNumber - 1);
    if (!paginationResult.isEmpty()) {
      currentPageNumber--;
      LOGGER.info("Current page: " + currentPageNumber);
      return paginationResult;
    }
    LOGGER.info("It is the first page");
    return getCurrentPage();
  }

  private List<BookDto> getPaginationResult(int pageNumber) {
    PaginationResult result = paginationResult.changePaginationResult(pageNumber);

    List<BookstoreBook> bookstoreBooks = result.getBookstoreBooksOnPage();

    return parseBookstoreBooksIntoBookDtos(bookstoreBooks);
  }
}
