package edition.academy.seventh.scrapping;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

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
