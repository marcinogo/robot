package edition.academy.seventh.display;

import edition.academy.seventh.database.model.BookDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static edition.academy.seventh.display.BookFilterType.*;

/** @author Kamil Rojek */
class EagerBookPaginationFilterHandler {
  private List<BookDto> books;
  private List<BookDto> booksBackup;

  EagerBookPaginationFilterHandler(List<BookDto> books) {
    this.books = this.booksBackup = books;
  }

  List<BookDto> changeFilter(BookFilterType bookFilterType) {
    switch (bookFilterType) {
      case PROMOTIONAL_PRICE_ASCENDING:
        return filter(PROMOTIONAL_PRICE_ASCENDING.comparator);
      case PROMOTIONAL_PRICE_DESCENDING:
        return filter(PROMOTIONAL_PRICE_DESCENDING.comparator);
      case PRICE_ASCENDING:
        return filter(PRICE_ASCENDING.comparator);
      case PRICE_DESCENDING:
        return filter(PRICE_DESCENDING.comparator);
      case TITLE_ASCENDING:
        return filter(TITLE_ASCENDING.comparator);
      case TITLE_DESCENDING:
        return filter(TITLE_DESCENDING.comparator);
      case DEFAULT:
      default:
        return restoreDefaultFilter();
    }
  }

  private List<BookDto> filter(Comparator<BookDto> comparator) {
    return books = books.stream().sorted(comparator).collect(Collectors.toList());
  }

  private List<BookDto> restoreDefaultFilter() {
    return booksBackup;
  }
}
