package edition.academy.seventh.display;

import java.util.List;

public interface Pagination <T> {
  List<T> startPagination();

  List<T> changePaginationSize(PaginationSize size);

  List<T> nextPage();

  List<T> previousPage();

  List<T> changeFilter(Filter filter);
}
