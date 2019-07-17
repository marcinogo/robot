package edition.academy.seventh.serivce;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class DataProviderForJsonParser {

  @DataProvider
  public static Object[][] dataProviderForJsonResponse() {
    return new Object[][] {
      {
        "https://api.itbook.store/1.0/new",
      },
      {
        "https://api.itbook.store/1.0/books/9781788476249",
      },
      {
        "https://api.itbook.store/1.0/books/9781789612769",
      },
      {
        "https://api.itbook.store/1.0/books/9781789344875",
      },
    };
  }
}
