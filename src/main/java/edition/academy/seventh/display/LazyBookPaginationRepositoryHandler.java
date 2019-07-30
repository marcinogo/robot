package edition.academy.seventh.display;

import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.model.BookstoreBook;

import javax.persistence.EntityManager;
import java.util.List;

import static edition.academy.seventh.display.PaginationSize.TWENTY;
import static edition.academy.seventh.repository.ModelParserIntoBookDtos.parseBookstoreBooksIntoBookDtos;

/** @author Kamil Rojek */
class LazyBookPaginationRepositoryHandler {
  BookFilterType bookFilterType = BookFilterType.DEFAULT;
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

  List<BookDto> getBooksPaginated() {
    EntityManager entityManager = connectorProvider.getEntityManager();
    List<BookstoreBook> resultList = filter(entityManager);

    return parseBookstoreBooksIntoBookDtos(resultList);
  }

  List<BookDto> nextPage() {
    startingRecord += paginationSize.value;
    endingRecord += paginationSize.value;
    return getBooksPaginated();
  }

  List<BookDto> previousPage() {
    startingRecord -= paginationSize.value;
    endingRecord -= paginationSize.value;

    if (startingRecord <= 1L) {
      startingRecord = 1L;
      endingRecord = paginationSize.value;
    }
    return getBooksPaginated();
  }

  List<BookDto> changePaginationSize(PaginationSize paginationSize) {
    initializePaginationValues(paginationSize);
    return getBooksPaginated();
  }

  private void initializePaginationValues(PaginationSize paginationSize) {
    this.paginationSize = paginationSize;
    startingRecord = 1L;
    endingRecord = this.paginationSize.value;
  }

  private List<BookstoreBook> filter(EntityManager entityManager) {
    return entityManager
        .createNativeQuery(bookFilterType.query, BookstoreBook.class)
        .setParameter("start", startingRecord)
        .setParameter("end", endingRecord)
        .getResultList();
  }
}
