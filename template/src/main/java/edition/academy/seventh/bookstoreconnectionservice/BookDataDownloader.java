package edition.academy.seventh.bookstoreconnectionservice;

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

    private final String BOOKSTORE_BOOK_URL;
    private final BookstoreConnector bookstoreConnector;

    @Autowired
    public BookDataDownloader(@Value("https://api.itbook.store/1.0/books/") String BOOKSTORE_BOOK_URL,
                              BookstoreConnector bookstoreConnector) {
        this.BOOKSTORE_BOOK_URL = BOOKSTORE_BOOK_URL;
        this.bookstoreConnector = bookstoreConnector;
    }

    List<String> listBooksByIsbn(List<String> isbns) {
        List<String> books = new ArrayList<>();
        for (String isbn : isbns) {
            books.add(bookstoreConnector.getJsonResponse(createBookFullUrl(isbn)));
        }
        return books;
    }

    private String createBookFullUrl(String isbn) {
        return new StringBuilder(BOOKSTORE_BOOK_URL).append(isbn).toString();
    }

}
