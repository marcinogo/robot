package edition.academy.seventh.display;

import edition.academy.seventh.database.model.BookDto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** @author Kamil Rojek */
public class EagerPagination implements Pagination{
  private List<BookDto> books;
  private Map<Integer, List<BookDto>> paginationMap;

  public EagerPagination(List<BookDto> books) {
    this.books = books;
  }

  @Override
  public List<BookDto> startPagination() {
    return null;
  }

  @Override
  public List<BookDto> changePagination(PaginationSize size) {
    return null;
  }

  @Override
  public List<BookDto> nextPage() {
    return null;
  }

  @Override
  public List<BookDto> previousPage() {
    return null;
  }

  @Override
  public List<BookDto> changeFilter(Filter filter) {
    return null;
  }

  public Map<Integer, List<BookDto>> createPagination(PaginationSize paginationSize) {
    paginationMap = new LinkedHashMap<>();
    int recordsCounter = 0;
    int pageNumber = 1;

    for (BookDto book : books) {
      if (++recordsCounter <= paginationSize.value) {
        updatePaginationMap(pageNumber, book);
        continue;
      }
      recordsCounter = 0;
      pageNumber++;
    }

    return paginationMap;
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
