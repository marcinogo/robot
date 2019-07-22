package edition.academy.seventh.service;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.service.mapper.ItBookMapper;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class BookMapperTest {

  private static List<BookDto> bookList;

  @DataProvider
  public static Object[][] dataProviderForJSONMapping() {
    return new Object[][] {
      {bookList.get(0).getTitle(), "Learning JavaScript"},
      {bookList.get(0).getSubtitle(), "A Hands-On Guide to the Fundamentals of Modern JavaScript"},
      {bookList.get(0).getAuthors(), "Tim Wright"},
      {bookList.get(0).getRetailPrice(), "$8.99"},
      {bookList.get(0).getImageLink(), "https://itbook.store/img/books/9780321832740.png"},
      {bookList.get(0).getHref(), "https://itbook.store/books/9780321832740"},
      {bookList.get(0).getBookstore(), "ITBookstore"}
    };
  }

  @BeforeTest
  public void prepareListOfBooks() throws IOException {
    ItBookMapper itBookMapper = new ItBookMapper();
    String bookJSON =
        "{\n"
            + " \"title\": \"Learning JavaScript\",\n"
            + " \"subtitle\": \"A Hands-On Guide to the Fundamentals of Modern JavaScript\",\n"
            + " \"authors\": \"Tim Wright\",\n"
            + " \"price\": \"$8.99\",\n"
            + " \"image\": \"https://itbook.store/img/books/9780321832740.png\",\n"
            + " \"url\": \"https://itbook.store/books/9780321832740\"\n"
            + "}";

    List<String> partialBook = List.of(bookJSON);
    bookList = itBookMapper.mapListOfJson(partialBook);
  }

  @Test(dataProvider = "dataProviderForJSONMapping")
  public void should_returnActualBookProperties_fromGivenJson(String bookPart, String expected) {
    assertEquals(bookPart, expected);
  }
}
