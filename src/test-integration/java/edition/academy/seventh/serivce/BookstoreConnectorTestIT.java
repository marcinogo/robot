package edition.academy.seventh.serivce;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class BookstoreConnectorTestIT {

  @Test(
      dataProviderClass = DataProviderForJsonParser.class,
      dataProvider = "dataProviderForJsonResponse")
  public void should_returnJsonWithBooksString_when_givenProperUrl(String url, String response) {
    // Given
    BookstoreConnector bookstoreConnector = new BookstoreConnector();

    // When
    String jsonResponse = bookstoreConnector.getJsonResponse(url);

    // Then
    assertEquals(jsonResponse, response);
  }
}
