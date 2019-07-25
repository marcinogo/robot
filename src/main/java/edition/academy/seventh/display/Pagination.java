package edition.academy.seventh.display;

import edition.academy.seventh.database.model.BookDto;

import java.util.List;

public interface Pagination {
  List<BookDto> startPagination();

  List<BookDto> changePaginationSize(PaginationSize size);

  List<BookDto> nextPage();

  List<BookDto> previousPage();

  List<BookDto> changeFilter(Filter filter);
}
