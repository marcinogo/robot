package edition.academy.seventh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcin Ogorzałek
 * @author Ola Podorska
 */
@Component
class BookDataDownloader {

  private final String bookstoreBookUrl;
  private final BookstoreConnector bookstoreConnector;

  @Autowired
  BookDataDownloader(
      @Value("https://api.itbook.store/1.0/books/") String bookstoreBookUrl,
      BookstoreConnector bookstoreConnector) {
    this.bookstoreBookUrl = bookstoreBookUrl;
    this.bookstoreConnector = bookstoreConnector;
  }

  List<String> listBooksByIsbn(List<String> isbns) {
    List<String> books = new ArrayList<>();
    for (String isbn : isbns) {
      String jsonResponse = generateValidJson(isbn);
      books.add(jsonResponse);
    }
    return books;
  }

  private String generateValidJson(String isbn) {
    String jsonResponse = bookstoreConnector.getJsonResponse(createBookFullUrl(isbn));
    jsonResponse = jsonResponse.replaceAll("\\$", "");
    jsonResponse = jsonResponse.replace("}",",\"currency\":\"$\"}");
    return jsonResponse;
  }

  private String createBookFullUrl(String isbn) {
    return bookstoreBookUrl + isbn;
  }
}
