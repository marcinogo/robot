package edition.academy.seventh.display;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.service.BookService;

import java.util.*;

/**
 * Eager implementation of {@link Pagination} interface based on {@link BookDto} object.
 *
 * <p>The {@code currentPageNumber}, {@code nextPage}, {@code previousPage}, {@code changePaginationSize}
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
  private int currentPageNumber;

  EagerBookPagination(BookService bookService) {
    this.books = bookService.getBooksFromDatabase();
    this.filterHandler = new EagerBookPaginationFilterHandler(books);
    initializePaginationMap(paginationSize);
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> currentPage() {
    return getPage(currentPageNumber);
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> nextPage() {
    if (paginationMap.keySet().size() > currentPageNumber) {
      return getPage(++currentPageNumber);
    }
    return getPage(currentPageNumber);
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> previousPage() {
    if (currentPageNumber > 1) {
      return getPage(--currentPageNumber);
    }
    return getPage(currentPageNumber);
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> changePaginationSize(PaginationSize size) {
    initializePaginationMap(size);
    return getPage(currentPageNumber);
  }

  /** {@inheritDoc} */
  @Override
  public List<BookDto> changeFilter(Filter filter) {
    books = filterHandler.changeFilter((BookFilter) filter);
    initializePaginationMap(paginationSize);
    return currentPage();
  }

  private void initializePaginationMap(PaginationSize paginationSize) {
    this.paginationMap = new LinkedHashMap<>();
    this.currentPageNumber = 1;
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

  private List<BookDto> getPage(int pageNumber) {
    if (paginationMap.isEmpty()) return new ArrayList<>();
    return paginationMap.get(pageNumber);
  }
}
