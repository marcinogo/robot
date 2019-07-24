package edition.academy.seventh.display;

import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;

import java.util.List;

/** @author Kamil Rojek */
public class LazyPagination {
  private List<BookDto> currentBooks;
  private LazyPaginationBookRepositoryHandler paginatonRepositoryHandler;

  public LazyPagination(ConnectorProvider connectorProvider) {
    this.paginatonRepositoryHandler = new LazyPaginationBookRepositoryHandler(connectorProvider);
  }

  public List<BookDto> startPagination() {
    return currentBooks = paginatonRepositoryHandler.getBookInPagination();
  }

  public List<BookDto> nextPage() {
    List<BookDto> nextBooks = paginatonRepositoryHandler.nextPage();

    if (nextBooks.isEmpty()) return currentBooks;
    return currentBooks = nextBooks;
  }

  public List<BookDto> previousPage() {
    List<BookDto> previousBooks = paginatonRepositoryHandler.previousPage();

    if (previousBooks.isEmpty()) return currentBooks;
    return currentBooks = previousBooks;
  }

  public List<BookDto> changeFilter(Filter filter) {
    paginatonRepositoryHandler.filter = filter;
    return startPagination();
  }
}
