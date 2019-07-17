package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class BookMapperTest {
  private static List<Book> bookList;

  @BeforeTest
  public void prepareListOfBooks() throws IOException {
    ItBookMapper itBookMapper = new ItBookMapper();
    String bookJSON =
        "{\n"
            + " \"title\": \"Learning JavaScript\",\n"
            + " \"subtitle\": \"A Hands-On Guide to the Fundamentals of Modern JavaScript\",\n"
            + " \"authors\": \"Tim Wright\",\n"
            + " \"price\": \"$8.99\",\n"
            + " \"url\": \"https://itbook.store/books/9780321832740\"\n"
            + "}";

    List<String> partialBook = List.of(bookJSON);
    bookList = itBookMapper.mapListOfJson(partialBook);
  }

  @DataProvider
  public static Object[][] dataProviderForJSONMapping() {
    return new Object[][] {
      {bookList.get(0).getTitle(), "Learning JavaScript"},
      {bookList.get(0).getSubtitle(), "A Hands-On Guide to the Fundamentals of Modern JavaScript"},
      {bookList.get(0).getAuthors(), "Tim Wright"},
      {bookList.get(0).getPrice(), "$8.99"}
    };
  }

  @Test(dataProvider = "dataProviderForJSONMapping")
  public void should_returnActualBookProperties_when_fromValidJson(
      String bookPart, String expected) {
    assertEquals(bookPart, expected);
  }
}
