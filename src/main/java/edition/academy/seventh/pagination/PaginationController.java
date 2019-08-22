package edition.academy.seventh.pagination;

import edition.academy.seventh.persistence.BookService;
import edition.academy.seventh.persistence.response.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controls the flow of books pagination pagination.
 *
 * @author Kamil Rojek
 */
@RestController
@CrossOrigin("${robot.crossorigin}")
class PaginationController {
  private Pagination<BookDto> pagination;

  /** Instantiate pagination object. */
  @Autowired
  PaginationController(BookService bookService) {
    this.pagination = PaginationFactory.createEagerBookPagination(bookService);
  }

  /**
   * Starts books pagination and retrieves records from the first page.
   *
   * @return {@code List<BookDto>} books.
   */
  @GetMapping("/books/pagination")
  ResponseEntity<List<BookDto>> getCurrentPage() {
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
  ResponseEntity<List<BookDto>> next() {
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
  ResponseEntity<List<BookDto>> previous() {
    List<BookDto> books = pagination.previousPage();
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  /**
   * Changes {@link PaginationSize pagination size} pagination.
   *
   * @param size {@link PaginationSize pagination size}.
   * @return {@code List<BookDto>} books.
   */
  @RequestMapping("/books/pagination/size")
  ResponseEntity<List<BookDto>> changePaginationSize(@RequestParam("value") PaginationSize size) {
    List<BookDto> books = pagination.changePaginationSize(size);
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  /**
   * Changes {@link BookFilterType books bookFilterType}.
   *
   * @param bookFilterType {@link BookFilterType books bookFilterType}.
   * @return {@code List<BookDto>} books.
   */
  @RequestMapping("/books/pagination/filter")
  ResponseEntity<List<BookDto>> setFilter(@RequestParam("type") BookFilterType bookFilterType) {
    List<BookDto> books = pagination.changeFilter(bookFilterType);
    return new ResponseEntity<>(books, HttpStatus.OK);
  }
}
