package edition.academy.seventh.display;

import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.BookstoreBook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

import static edition.academy.seventh.repository.BookParser.parseBookstoreBookListIntoDTBookList;

/** @author Kamil Rojek */
public class DynamicPagination {
  private final ConnectorProvider connectorProvider;
  private PaginationSize paginationSize = PaginationSize.TWENTY;
  private List<BookDto> currentBooks;
  private Filter filter;
  private long startingRecord = 1L;
  private long endingRecord = paginationSize.value;

  @Autowired
  public DynamicPagination(ConnectorProvider connectorProvider, Filter filter) {
    this.connectorProvider = connectorProvider;
    this.filter = filter;
  }

  public List<BookDto> startPagination() {
    return currentBooks = getBookInPagination();
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

  private List<BookDto> getBookInPagination() {
    EntityManager entityManager = connectorProvider.getEntityManager();
    List<BookstoreBook> resultList = filter(entityManager);
    return parseBookstoreBookListIntoDTBookList(resultList);
  }

  private List<BookstoreBook> filter(EntityManager entityManager) {
    return entityManager
        .createNativeQuery(filter.currentFilter, BookstoreBook.class)
        .setParameter("start", startingRecord)
        .setParameter("end", endingRecord)
        .getResultList();
  }
}
