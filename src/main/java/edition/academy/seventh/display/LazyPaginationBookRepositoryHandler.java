package edition.academy.seventh.display;

import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.BookstoreBook;

import javax.persistence.EntityManager;
import java.util.List;

import static edition.academy.seventh.repository.BookParser.parseBookstoreBookListIntoDTBookList;

/** @author Kamil Rojek */
class LazyPaginationBookRepositoryHandler {
  Filter filter = Filter.DEFAULT;
  private ConnectorProvider connectorProvider;
  private PaginationSize paginationSize = PaginationSize.TWENTY;
  private long startingRecord = 1L;
  private long endingRecord = paginationSize.value;

  LazyPaginationBookRepositoryHandler(ConnectorProvider connectorProvider) {
    this.connectorProvider = connectorProvider;
  }

  List<BookDto> getBookInPagination() {
    EntityManager entityManager = connectorProvider.getEntityManager();
    List<BookstoreBook> resultList = filter(entityManager);

    //todo Problem opisany w kwitku na gitkrakenie #148
    //connectorProvider.close();
    return parseBookstoreBookListIntoDTBookList(resultList);
  }

  List<BookDto> nextPage() {
    startingRecord += paginationSize.value;
    endingRecord += paginationSize.value;
    return getBookInPagination();
  }

  List<BookDto> previousPage() {
    startingRecord -= paginationSize.value;
    endingRecord -= paginationSize.value;

    if (startingRecord <= 1L) {
      startingRecord = 1L;
      endingRecord = paginationSize.value;
    }
    return getBookInPagination();
  }

  private List<BookstoreBook> filter(EntityManager entityManager) {
    return entityManager
        .createNativeQuery(filter.query, BookstoreBook.class)
        .setParameter("start", startingRecord)
        .setParameter("end", endingRecord)
        .getResultList();
  }
}
