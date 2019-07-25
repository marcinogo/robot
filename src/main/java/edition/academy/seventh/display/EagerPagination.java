package edition.academy.seventh.display;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.service.BookService;

import java.util.*;
import java.util.stream.Collectors;

/** @author Kamil Rojek */
public class EagerPagination implements Pagination {
  private List<BookDto> books;
  private List<BookDto>
      booksBackup; // todo nie mogę posortować ponownie po ID książek więc żeby wrócić do domyślnego
  // filtru muszę zrobić backup
  private Map<Integer, List<BookDto>> paginationMap;
  private int currentPage;
  private PaginationSize paginationSize = PaginationSize.TWENTY;

  public EagerPagination(BookService bookService) {
    this.books = this.booksBackup = bookService.getBooksFromDatabase();
    initializePagination(paginationSize);
  }

  @Override
  public List<BookDto> startPagination() {
    return paginationMap.get(currentPage);
  }

  @Override
  public List<BookDto> changePagination(PaginationSize size) {
    initializePagination(size);
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
    switch (filter) {
      case TITLE_DESCENDING:
        return filter(Comparator.comparing(BookDto::getTitle, Comparator.reverseOrder()));
      case PROMOTIONAL_PRICE_ASCENDING:
        return filter(Comparator.comparing(BookDto::getPromotionalPrice));
      case PROMOTIONAL_PRICE_DESCENDING:
        return filter(
            Comparator.comparing(BookDto::getPromotionalPrice, Comparator.reverseOrder()));
      case PRICE_ASCENDING:
        return filter(Comparator.comparing(BookDto::getRetailPrice));
      case PRICE_DESCENDING:
        return filter(Comparator.comparing(BookDto::getRetailPrice, Comparator.reverseOrder()));
      case TITLE_ASCENDING:
        return filter(Comparator.comparing(BookDto::getTitle));
      case DEFAULT:
      default:
        return restoreDefaultFilter();
    }
  }

  private List<BookDto> filter(Comparator<BookDto> comparator) {
    books = books.stream().sorted(comparator).collect(Collectors.toList());
    initializePagination(paginationSize);
    return startPagination();
  }

  private List<BookDto> restoreDefaultFilter() {
    books = booksBackup;
    initializePagination(paginationSize);
    return startPagination();
  }

  private void initializePagination(PaginationSize paginationSize) {
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
