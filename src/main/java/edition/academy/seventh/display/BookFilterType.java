package edition.academy.seventh.display;

import edition.academy.seventh.database.model.BookDto;

import java.util.Comparator;

/**
 * Available {@link Pagination} book filters. Each {@code Enum constant} implements value
 * initialized with native SQL query adequates to the name meaning.
 *
 * @author Kamil Rojek
 */
public enum BookFilterType implements Filter {

  /** Sorts in ascending manner books by ID. */
  DEFAULT("SELECT * FROM bookstore_book WHERE id between :start and :end"),

  /** Sorts in ascending manner books by promotional price. */
  PROMOTIONAL_PRICE_ASCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY promotional_price)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end",
      Comparator.comparing(BookDto::getPromotionalPrice)),

  /** Sorts in descending manner books by promotional price. */
  PROMOTIONAL_PRICE_DESCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY promotional_price DESC)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end",
      Comparator.comparing(BookDto::getPromotionalPrice, Comparator.reverseOrder())),

  /** Sorts in ascending manner books by retail price. */
  PRICE_ASCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY retail_price)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end",
      Comparator.comparing(BookDto::getRetailPrice)),

  /** Sorts in descending manner books by retail price. */
  PRICE_DESCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY retail_price DESC)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end",
      Comparator.comparing(BookDto::getRetailPrice, Comparator.reverseOrder())),

  /** Sorts in ascending manner books by title. */
  TITLE_ASCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY title)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end",
      Comparator.comparing(BookDto::getTitle)),

  /** Sorts in descending manner books by title. */
  TITLE_DESCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY title DESC)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end",
      Comparator.comparing(BookDto::getTitle, Comparator.reverseOrder()));

  /** Returns {@code Enum constant} variable. */
  String query;

  /** Returns {@code Comparator<BookDto>} proper to {@code BookFilterType enum constant}. */
  Comparator<BookDto> comparator;

  BookFilterType(String query) {
    this.query = query;
  }

  BookFilterType(String query, Comparator<BookDto> comparator) {
    this.query = query;
    this.comparator = comparator;
  }
}
