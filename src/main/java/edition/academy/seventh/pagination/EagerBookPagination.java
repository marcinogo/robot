package edition.academy.seventh.pagination;

import edition.academy.seventh.persistence.BookService;
import edition.academy.seventh.persistence.response.BookDto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Eager implementation of {@link Pagination} interface based on {@link BookDto} object.
 *
 * <p>The {@code currentPage}, {@code nextPage}, {@code previousPage}, {@code changePaginationSize}
 * and {@code changeFilter} methods operate on objects located in cache memory.
 *
 * <p>Default {@link PaginationSize} is set to <b>twenty</b> records per page.
 *
 * @see LazyBookPagination lazy book pagination implemenation.
 * @author Kamil Rojek
 */
class EagerBookPagination implements Pagination<BookDto> {
  private List<BookDto> books;
  private EagerBookPaginationFilterHandler filterHandler;
  private Map<Integer, List<BookDto>> paginationMap;
  private PaginationSize paginationSize = PaginationSize.TWENTY;
  private int currentPage;

  EagerBookPagination(BookService bookService) {
    this.books = bookService.getBooksFromDatabase();
    this.filterHandler = new EagerBookPaginationFilterHandler(books);
    initializePaginationMap(paginationSize);
  }

  private void initializePaginationMap(PaginationSize paginationSize) {
    this.paginationMap = new LinkedHashMap<>();
    this.currentPage = 1;
    this.paginationSize = paginationSize;
    int recordsCounter = 0;
    int pageNumber = 1;

    for (BookDto book : books) {
      if (++recordsCounter <= this.paginationSize.value) {
        updatePaginationMap(pageNumber, book);
        continue;
      }
      recordsCounter = 0;
      pageNumber++;
    }
  }

  private void updatePaginationMap(int pageNumber, BookDto book) {
    if (!paginationMap.containsKey(pageNumber)) {
      paginationMap.put(pageNumber, new ArrayList<>());
    }

    List<BookDto> books = paginationMap.get(pageNumber);
    books.add(book);
    paginationMap.put(pageNumber, books);
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> currentPage() {
    return paginationMap.get(currentPage);
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> nextPage() {
    if (paginationMap.keySet().size() > currentPage) {
      return paginationMap.get(++currentPage);
    }
    return paginationMap.get(currentPage);
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> previousPage() {
    if (currentPage > 1) {
      return paginationMap.get(--currentPage);
    }
    return paginationMap.get(currentPage);
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> changePaginationSize(PaginationSize size) {
    initializePaginationMap(size);
    return paginationMap.get(currentPage);
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> changeFilter(Filter filter) {
    books = filterHandler.changeFilter((BookFilterType) filter);
    initializePaginationMap(paginationSize);
    return currentPage();
  }
}
