package edition.academy.seventh.display;

import org.springframework.stereotype.Service;

/** @author Kamil Rojek */
@Service
public class Filter {
  private final String startParameter = ":start";
  private final String endParameter = ":end";
  String currentFilter;

  {
    setDefault();
  }

  public void setDefault() {
    currentFilter =
        String.format(
            "SELECT * FROM bookstore_book WHERE id between %s and %s",
            startParameter, endParameter);
  }

  public void setPriceAcending() {
    currentFilter =
        String.format(
            "SELECT * FROM ("
                + "  SELECT *, ROW_NUMBER () OVER (ORDER BY promotional_price)"
                + "  FROM bookstore_book"
                + ") x WHERE ROW_NUMBER BETWEEN %s AND %s",
            startParameter, endParameter);
  }
}
