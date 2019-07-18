package edition.academy.seventh.serivce;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Return books from ItBook bookstore.
 *
 * @author Marcin Ogorzałek
 * @author Ola Podorska
 */
@Service
public class ItbookBookstoreConnectionService implements BookstoreConnectionService {

  private final String bookstoreAllBooksUrl;
  private final BookstoreConnector bookstoreConnector;
  private final BookDataDownloader bookDataDownloader;
  private final JsonDataProcessor jsonDataProcessor;

  @Autowired
  public ItbookBookstoreConnectionService(
      @Value("https://api.itbook.store/1.0/new") String bookstoreUrl,
      BookstoreConnector bookstoreConnector,
      BookDataDownloader bookDataDownloader,
      JsonDataProcessor jsonDataProcessor) {
    this.bookstoreAllBooksUrl = bookstoreUrl;
    this.bookstoreConnector = bookstoreConnector;
    this.bookDataDownloader = bookDataDownloader;
    this.jsonDataProcessor = jsonDataProcessor;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Connection url is <a
   * href="https://api.itbook.store/1.0/new">https://api.itbook.store/1.0/new</a>
   *
   * @return {@inheritDoc}
   */
  @Override
  public List<String> getListOfBooksAsString() {
    bookstoreConnector.getJsonResponse(bookstoreAllBooksUrl);
    String booksAsJson = bookstoreConnector.getJsonResponse(bookstoreAllBooksUrl);
    JsonNode mappedBooksJson = jsonDataProcessor.mapJsonString(booksAsJson);
    List<String> isbns = jsonDataProcessor.convertJsonToBookIsbn(mappedBooksJson);
    return bookDataDownloader.listBooksByIsbn(isbns);
  }
}
