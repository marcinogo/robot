package edition.academy.seventh.service;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

@Test
public class BookstoreConnectorTestIT {
  @Test(
      dataProviderClass = DataProviderForJsonParser.class,
      dataProvider = "dataProviderForJsonResponse")
  public void should_returnJsonWithBooksString_when_givenProperUrl(String url) {
    // Given
    BookstoreConnector bookstoreConnector = new BookstoreConnector();

    // When
    String jsonResponse = bookstoreConnector.getJsonResponse(url);

    // Then
    assertNotNull(jsonResponse);
  }
}
