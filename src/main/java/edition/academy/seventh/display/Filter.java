package edition.academy.seventh.display;

/** @author Kamil Rojek */
public enum Filter {
  DEFAULT("SELECT * FROM bookstore_book WHERE id between :start and :end"),

  PROMOTIONAL_PRICE_ASCENDING(
      "SELECT * FROM ("
          + "  SELECT *, ROW_NUMBER () OVER (ORDER BY promotional_price)"
          + "  FROM bookstore_book"
          + ") x WHERE ROW_NUMBER BETWEEN :start AND :end");

  //  PROMOTIONAL_PRICE_DESCENDING,

  //  PRICE_ASCENDING,

  //  PRICE_DESCENDING,

  //  TITLE_ASCENDING,

  //  TITLE,

  //  DESCENDING;

  String query;

  Filter(String query) {
    this.query = query;
  }
}
