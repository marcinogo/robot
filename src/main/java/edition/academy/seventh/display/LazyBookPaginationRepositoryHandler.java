package edition.academy.seventh.display;

import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.BookstoreBook;

import javax.persistence.EntityManager;
import java.util.List;

import static edition.academy.seventh.display.PaginationSize.*;
import static edition.academy.seventh.repository.BookParser.parseBookstoreBookListIntoDTBookList;

/** @author Kamil Rojek */
class LazyBookPaginationRepositoryHandler {
  BookFilter bookFilter = BookFilter.DEFAULT;
  private ConnectorProvider connectorProvider;
  private PaginationSize paginationSize;
  private long startingRecord;
  private long endingRecord;

  {
    initializePaginationValues(TWENTY);
  }

  LazyBookPaginationRepositoryHandler(ConnectorProvider connectorProvider) {
    this.connectorProvider = connectorProvider;
  }

  List<BookDto> getBookInPagination() {
    EntityManager entityManager = connectorProvider.getEntityManager();
    List<BookstoreBook> resultList = filter(entityManager);

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

  List<BookDto> changePaginationSize(PaginationSize paginationSize) {
    initializePaginationValues(paginationSize);
    return getBookInPagination();
  }

  private void initializePaginationValues(PaginationSize paginationSize) {
    this.paginationSize = paginationSize;
    startingRecord = 1L;
    endingRecord = this.paginationSize.value;
  }

  // todo przerobienie zapytań na HQL
  private List<BookstoreBook> filter(EntityManager entityManager) {
    return entityManager
        .createNativeQuery(bookFilter.query, BookstoreBook.class)
        .setParameter("start", startingRecord)
        .setParameter("end", endingRecord)
        .getResultList();
  }
}
