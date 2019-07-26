package edition.academy.seventh.display;

import edition.academy.seventh.database.model.BookDto;
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

  /** Instantiate pagination object. */
  public PaginationController() {
    this.pagination = PaginationFactory.createEagerBookPagination();
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
   * Changes {@link BookFilter books bookFilter}.
   *
   * @param bookFilter {@link BookFilter books bookFilter}.
   * @return {@code List<BookDto>} books.
   */
  @RequestMapping("/books/pagination/filter")
  public ResponseEntity<List<BookDto>> setFilter(@RequestParam("type") BookFilter bookFilter) {
    List<BookDto> books = pagination.changeFilter(bookFilter);
    return new ResponseEntity<>(books, HttpStatus.OK);
  }
}
