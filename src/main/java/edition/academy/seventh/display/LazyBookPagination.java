package edition.academy.seventh.display;

import edition.academy.seventh.database.connector.ConnectorProvider;
import edition.academy.seventh.database.model.BookDto;

import java.util.List;

/**
 * Lazy implementation of {@link Pagination} interface based on {@link BookDto} object.
 *
 * <p>The {@code currentPage}, {@code nextPage}, {@code previousPage}, {@code changePaginationSize}
 * and {@code changeFilter} methods operate on SQL queries located in {@link Filter} enum.
 *
 * <p>Default {@link PaginationSize} is set to <b>twenty</b> records per page.
 *
 * @author Kamil Rojek
 */
public class LazyBookPagination implements Pagination<BookDto> {
  private List<BookDto> currentBooks;
  private LazyBookPaginationRepositoryHandler paginatonRepositoryHandler;

  public LazyBookPagination(ConnectorProvider connectorProvider) {
    this.paginatonRepositoryHandler = new LazyBookPaginationRepositoryHandler(connectorProvider);
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> currentPage() {
    return currentBooks = paginatonRepositoryHandler.getBookInPagination();
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> nextPage() {
    List<BookDto> nextBooks = paginatonRepositoryHandler.nextPage();

    if (nextBooks.isEmpty()) return currentBooks;
    return currentBooks = nextBooks;
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> previousPage() {
    List<BookDto> previousBooks = paginatonRepositoryHandler.previousPage();

    if (previousBooks.isEmpty()) return currentBooks;
    return currentBooks = previousBooks;
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> changePaginationSize(PaginationSize size) {
    return currentBooks = paginatonRepositoryHandler.changePaginationSize(size);
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> changeFilter(Filter filter) {
    paginatonRepositoryHandler.filter = filter;
    return currentPage();
  }
}
