package edition.academy.seventh.repository;

import org.testng.annotations.DataProvider;

public class DataProviderForBookParser {

  @DataProvider
  public static Object[][] dataProviderForBookParsing() {
    return new Object[][]{
        {"Title", "Subtitle", "Author", "50", "40", "http", "http2", "Bookstore"},
        {"Title2", "Subtitle2", "Author2", "5", "4", "https", "https2", "Bookstore2"},
        {"2Title", "2Subtitle", "2Author", "150", "140", "2http", "2http2", "2Bookstore"}
    };
  }
}
