package edition.academy.seventh.persistence;

import org.testng.annotations.DataProvider;

import java.math.BigDecimal;

public class DataProviderForBookParser {

  @DataProvider
  public static Object[][] dataProviderForBookParsing() {
    return new Object[][] {
      {
        "Title",
        "Subtitle",
        "Author",
        new BigDecimal("50"),
        new BigDecimal("40"),
        "zł",
        "http",
        "http2",
        "Bookstore"
      },
      {
        "Title2",
        "Subtitle2",
        "Author2",
        new BigDecimal("5"),
        new BigDecimal("4"),
        "zł",
        "https",
        "https2",
        "Bookstore2"
      },
      {
        "2Title",
        "2Subtitle",
        "2Author",
        new BigDecimal("150"),
        new BigDecimal("140"),
        "zł",
        "2http",
        "2http2",
        "2Bookstore"
      }
    };
  }
}
