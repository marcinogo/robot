package edition.academy.seventh.display;

/** @author Kamil Rojek */
public enum Filter {
  DEFAULT("SELECT * FROM bookstore_book WHERE id between :start and :end"),

  PROMOTIONAL_PRICE_ASCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY promotional_price)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end"),

  PROMOTIONAL_PRICE_DESCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY promotional_price DESC)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end"),

  PRICE_ASCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY retail_price)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end"),

  PRICE_DESCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY retail_price DESC)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end"),

  TITLE_ASCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY title)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end"),

  TITLE_DESCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY title DESC)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end");

  String query;

  Filter(String query) {
    this.query = query;
  }
}
