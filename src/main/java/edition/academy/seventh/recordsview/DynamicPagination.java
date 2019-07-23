package edition.academy.seventh.recordsview;

import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.BookstoreBook;

import javax.persistence.EntityManager;
import java.util.List;

import static edition.academy.seventh.repository.BookParser.parseBookstoreBookListIntoDTBookList;

/** @author Kamil Rojek */
public class DynamicPagination {
  private final ConnectorProvider connectorProvider;
  private PaginationSize paginationSize = PaginationSize.TWO;
  private List<BookDto> currentBooks;
  private long startingRecord = 1L;
  private long endingRecord = paginationSize.value;

  public DynamicPagination(ConnectorProvider connectorProvider) {
    this.connectorProvider = connectorProvider;
  }

  public List<BookDto> startPagination() {
    return currentBooks = getBookInPagination();
  }

  private List<BookDto> getBookInPagination() {
    EntityManager entityManager = connectorProvider.getEntityManager();
    String hql = "from bookstore_book as s where s.id between 1 and " + paginationSize;
    List<BookstoreBook> resultList =
        entityManager
            .createQuery(
                "from bookstore_book as s where s.id between :start and :end", BookstoreBook.class)
            .setParameter("start", startingRecord)
            .setParameter("end", endingRecord)
            .getResultList();
    return parseBookstoreBookListIntoDTBookList(resultList);
  }

  public List<BookDto> nextPage() {
    startingRecord += paginationSize.value;
    endingRecord += paginationSize.value;
    List<BookDto> bookInPagination = getBookInPagination();

    if (bookInPagination.isEmpty()) return currentBooks;
    return currentBooks = bookInPagination;
  }

  public List<BookDto> previousPage() {
    startingRecord -= paginationSize.value;
    endingRecord -= paginationSize.value;

    if (startingRecord <= 1L) {
      startingRecord = 1L;
      endingRecord = paginationSize.value;
    }

    List<BookDto> bookInPagination = getBookInPagination();

    if (bookInPagination.isEmpty()) return currentBooks;
    return currentBooks = bookInPagination;
  }
}
