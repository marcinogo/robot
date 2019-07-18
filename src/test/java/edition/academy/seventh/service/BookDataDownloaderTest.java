package edition.academy.seventh.service;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.List;
import org.testng.annotations.Test;

@Test
public class BookDataDownloaderTest {

  @Test(
      dataProviderClass = DataProviderForConvertingIsbnToBook.class,
      dataProvider = "dataProviderForConvertingIsbnToBook")
  public void should_returnListOfBooksAsString_when_haveListOfIsbns(
      List<String> isbns, List<String> expectedBooks) {
    // Given
    BookstoreConnector bookstoreConnector = mock(BookstoreConnector.class);
    when(bookstoreConnector.getJsonResponse(anyString())).thenReturn(expectedBooks.get(0));

    BookDataDownloader bookDataDownloader =
        new BookDataDownloader("https://api.itbook.store/1.0/books/", bookstoreConnector);

    // When
    List<String> books = bookDataDownloader.listBooksByIsbn(isbns);

    // Then
    assertEquals(books, expectedBooks);
  }
}
