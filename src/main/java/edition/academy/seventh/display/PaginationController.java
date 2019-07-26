package edition.academy.seventh.display;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controls the flow of books pagination display.
 *
 * @author Kamil Rojek
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
class PaginationController {
  private Pagination<BookDto> pagination;

  @Autowired
  public PaginationController(BookService bookService) {
    //    this.pagination = new LazyBookPagination(ConnectorFactory.of(POSTGRESQL));
    this.pagination = new EagerBookPagination(bookService);
  }

  /**
   * Starts books pagination and retrieves records from the first page.
   *
   * @return {@code List<BookDto>} books.
   */
  @GetMapping("/books/pagination")
  public ResponseEntity<List<BookDto>> getCurrentPage() {
    List<BookDto> books = pagination.currentPage();
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  /**
   * Retrieves {@code List<BookDto>} books from the next page. If no books found retrieves books
   * from current page.
   *
   * @return {@code List<BookDto>} books.
   */
  @GetMapping("/books/pagination/next")
  public ResponseEntity<List<BookDto>> next() {
    List<BookDto> books = pagination.nextPage();
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  /**
   * Retrieves {@code List<BookDto>} books from the previous page. If no books found retrieves books
   * from current page.
   *
   * @return {@code List<BookDto>} books.
   */
  @GetMapping("/books/pagination/previous")
  public ResponseEntity<List<BookDto>> previous() {
    List<BookDto> books = pagination.previousPage();
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  /**
   * Changes {@link PaginationSize pagination size} display.
   *
   * @param size {@link PaginationSize pagination size}.
   * @return {@code List<BookDto>} books.
   */
  @RequestMapping("/books/pagination/size")
  public ResponseEntity<List<BookDto>> changePaginationSize(
      @RequestParam("value") PaginationSize size) {
    List<BookDto> books = pagination.changePaginationSize(size);
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  /**
   * Changes {@link Filter books filter}.
   *
   * @param filter {@link Filter books filter}.
   * @return {@code List<BookDto>} books.
   */
  @RequestMapping("/books/pagination/filter")
  public ResponseEntity<List<BookDto>> setFilter(@RequestParam("type") Filter filter) {
    List<BookDto> books = pagination.changeFilter(filter);
    return new ResponseEntity<>(books, HttpStatus.OK);
  }
}
