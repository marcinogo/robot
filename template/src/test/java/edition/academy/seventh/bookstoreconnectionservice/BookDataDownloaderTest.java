package edition.academy.seventh.bookstoreconnectionservice;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Ola Podorska
 */
@Test
public class BookDataDownloaderTest {

  @Test(
      dataProviderClass = DataProviderForJsonParser.class,
      dataProvider = "dataProviderForConvertingIsbnToBook")
  public void should_returnListOfBooksAsString_when_haveListOfIsbns(
      List<String> isbns, List<String> expectedBooks) {
    // Given
    BookDataDownloader bookDataDownloader =
        new BookDataDownloader("https://api.itbook.store/1.0/books/", new BookstoreConnector());

    // When
    List<String> books = bookDataDownloader.listBooksByIsbn(isbns);

    // Then
    Assert.assertEquals(books, expectedBooks);
  }
}
