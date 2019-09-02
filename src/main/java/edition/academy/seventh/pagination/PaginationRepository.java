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
public class PaginationRepository {

  private static final Logger logger = LoggerFactory.getLogger(PaginationRepository.class);
  private int currentPageNumber = 1;
  private ConnectorProvider connectorProvider;
  private PaginationResult paginationResult;

  @Autowired
  public PaginationRepository(@Value("${robot.db}") String database) {
    connectorProvider = ConnectorFactory.of(DatabaseType.valueOf(database));
    paginationResult = initializePaginationResult(connectorProvider.getEntityManager());
  }

  public void updatePaginationResult() {
    paginationResult = initializePaginationResult(connectorProvider.getEntityManager());
  }

  List<BookDto> getCurrentPage() {
    return getPaginationResult(currentPageNumber);
  }

  List<BookDto> getNextPage() {
    List<BookDto> paginationResult = getPaginationResult(currentPageNumber + 1);
    if (paginationResult != null) {
      currentPageNumber++;
      return paginationResult;
    }
    return getCurrentPage();
  }

  List<BookDto> getPreviousPage() {
    List<BookDto> paginationResult = getPaginationResult(currentPageNumber - 1);
    if (paginationResult != null) {
      currentPageNumber--;
      return paginationResult;
    }
    return getCurrentPage();
  }

  private List<BookDto> getPaginationResult(int pageNumber) {
    PaginationResult result = paginationResult.changePaginationResult(pageNumber);

    List<BookstoreBook> bookstoreBooks = result.getList();

    return parseBookstoreBooksIntoBookDtos(bookstoreBooks);
  }

  private PaginationResult initializePaginationResult(EntityManager entityManager) {
    try {

      Session session = entityManager.unwrap(Session.class);
      String sql = "FROM " + BookstoreBook.class.getName();
      Query<BookstoreBook> query = session.createQuery(sql, BookstoreBook.class);

      return new PaginationResult(query);
    } finally {
      entityManager.close();
      connectorProvider.close();
    }
  }
}
