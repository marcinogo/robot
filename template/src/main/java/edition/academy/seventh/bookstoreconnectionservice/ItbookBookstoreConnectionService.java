package edition.academy.seventh.bookstoreconnectionservice;

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

  private final String BOOKSTORE_ALL_BOOKS_URL;
  private final BookstoreConnector bookstoreConnector;
  private final BookDataDownloader bookDataDownloader;
  private final JsonDataProcessor jsonDataProcessor;

  @Autowired
  public ItbookBookstoreConnectionService(
      @Value("https://api.itbook.store/1.0/new") String BOOKSTORE_URL,
      BookstoreConnector bookstoreConnector,
      BookDataDownloader bookDataDownloader,
      JsonDataProcessor jsonDataProcessor) {
    this.BOOKSTORE_ALL_BOOKS_URL = BOOKSTORE_URL;
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
    bookstoreConnector.getJsonResponse(BOOKSTORE_ALL_BOOKS_URL);
    String booksAsJson = bookstoreConnector.getJsonResponse(BOOKSTORE_ALL_BOOKS_URL);
    JsonNode mappedBooksJson = jsonDataProcessor.mapJsonString(booksAsJson);
    List<String> isbns = jsonDataProcessor.convertJsonToBookIsbn(mappedBooksJson);
    return bookDataDownloader.listBooksByIsbn(isbns);
  }
}
