package edition.academy.seventh.display;

import edition.academy.seventh.database.model.BookDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/** @author Kamil Rojek */
class EagerBookPaginationFilterHandler {
  private List<BookDto> books;
  private List<BookDto> booksBackup;

  EagerBookPaginationFilterHandler(List<BookDto> books) {
    this.books = this.booksBackup = books;
  }

  List<BookDto> changeFilter(BookFilter bookFilter) {
    switch (bookFilter) {
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
    return books = books.stream().sorted(comparator).collect(Collectors.toList());
  }

  private List<BookDto> restoreDefaultFilter() {
    return booksBackup;
  }
}
