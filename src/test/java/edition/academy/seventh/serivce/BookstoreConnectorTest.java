package edition.academy.seventh.serivce;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Ola Podorska
 */
@Test
public class BookstoreConnectorTest {

  @Test(
      dataProviderClass = DataProviderForJsonParser.class,
      dataProvider = "dataProviderForJsonResponse")
  public void should_returnJsonWithBooksString_when_givenProperUrl(String url, String response) {
    // Given
    BookstoreConnector bookstoreConnector = new BookstoreConnector();

    // When
    String jsonResponse = bookstoreConnector.getJsonResponse(url);

    // Then
    Assert.assertEquals(jsonResponse, response);
  }
}
