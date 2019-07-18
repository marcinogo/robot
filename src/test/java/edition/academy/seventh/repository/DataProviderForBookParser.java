package edition.academy.seventh.repository;

import org.testng.annotations.DataProvider;

public class DataProviderForBookParser {

  @DataProvider
  public static Object[][] dataProviderForBookParsing() {
    return new Object[][] {
      {"Title", "Subtitle", "Author", "Bookstore", "50", "40", "http", "http2"},
      {"Title2", "Subtitle2", "Author2", "Bookstore2", "5", "4", "https", "https2"},
      {"2Title", "2Subtitle", "2Author", "2Bookstore", "150", "140", "2http", "2http2"}
    };
  }
}
