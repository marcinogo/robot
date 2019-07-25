package edition.academy.seventh.display;

import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;

import java.util.List;

/** @author Kamil Rojek */
public class LazyPagination implements Pagination {
  private List<BookDto> currentBooks;
  private LazyPaginationBookRepositoryHandler paginatonRepositoryHandler;

  public LazyPagination(ConnectorProvider connectorProvider) {
    this.paginatonRepositoryHandler = new LazyPaginationBookRepositoryHandler(connectorProvider);
  }

  @Override
  public List<BookDto> startPagination() {
    return currentBooks = paginatonRepositoryHandler.getBookInPagination();
  }

  @Override
  public List<BookDto> changePaginationSize(PaginationSize size) {
    return currentBooks = paginatonRepositoryHandler.changePaginationSize(size);
  }

  @Override
  public List<BookDto> nextPage() {
    List<BookDto> nextBooks = paginatonRepositoryHandler.nextPage();

    if (nextBooks.isEmpty()) return currentBooks;
    return currentBooks = nextBooks;
  }

  @Override
  public List<BookDto> previousPage() {
    List<BookDto> previousBooks = paginatonRepositoryHandler.previousPage();

    if (previousBooks.isEmpty()) return currentBooks;
    return currentBooks = previousBooks;
  }

  @Override
  public List<BookDto> changeFilter(Filter filter) {
    paginatonRepositoryHandler.filter = filter;
    return startPagination();
  }
}
