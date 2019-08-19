package edition.academy.seventh.controller;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.database.model.BookstoreBookDto;
import edition.academy.seventh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/** @author Kamil Rojek */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
class BookRetriever {
  private BookService bookService;

  @Autowired
  public BookRetriever(BookService bookService) {
    this.bookService = bookService;
  }

  /**
   * Retrieves list of books from database.
   *
   * @return List of books.
   */
  @GetMapping("/books")
  public ResponseEntity<List<BookDto>> getBooks() {
    return new ResponseEntity<>(bookService.getBooksFromDatabase(), HttpStatus.OK);
  }

  /**
   * Retrieves specific book from database based on its URL.
   *
   * @return {@link BookstoreBookDto}.
   */
  @GetMapping("/bookUrl/**")
  public ResponseEntity<BookstoreBookDto> getBookstoreBook(HttpServletRequest request) {

    String hyperlink = request.getRequestURI();
    hyperlink = hyperlink.substring(9);

    return new ResponseEntity<>(bookService.getBookstoreBookDtoByHref(hyperlink), HttpStatus.OK);
  }
}
