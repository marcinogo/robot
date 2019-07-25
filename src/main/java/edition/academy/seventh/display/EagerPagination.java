package edition.academy.seventh.display;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.service.BookService;

import java.util.*;

/** @author Kamil Rojek */
public class EagerPagination implements Pagination {
  private List<BookDto> books;
  private EagerPaginationFilterHandler filterHandler;
  private Map<Integer, List<BookDto>> paginationMap;
  private PaginationSize paginationSize = PaginationSize.TWENTY;
  private int currentPage;

  public EagerPagination(BookService bookService) {
    this.books = bookService.getBooksFromDatabase();
    this.filterHandler = new EagerPaginationFilterHandler(books);
    initializePaginationMap(paginationSize);
  }

  @Override
  public List<BookDto> startPagination() {
    return paginationMap.get(currentPage);
  }

  @Override
  public List<BookDto> changePaginationSize(PaginationSize size) {
    initializePaginationMap(size);
    return paginationMap.get(currentPage);
  }

  @Override
  public List<BookDto> nextPage() {
    if (paginationMap.keySet().size() > currentPage) {
      return paginationMap.get(++currentPage);
    }
    return paginationMap.get(currentPage);
  }

  @Override
  public List<BookDto> previousPage() {
    if (currentPage > 1) {
      return paginationMap.get(--currentPage);
    }
    return paginationMap.get(currentPage);
  }

  @Override
  public List<BookDto> changeFilter(Filter filter) {
    books = filterHandler.changeFilter(filter);
    initializePaginationMap(paginationSize);
    return startPagination();
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
}
