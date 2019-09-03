package edition.academy.seventh.pagination;

import edition.academy.seventh.persistence.response.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controls the flow of books pagination.
 *
 * @author Agnieszka Trzewik
 */
@RestController
@CrossOrigin("${robot.crossorigin}")
class PaginationController {
  private final PaginationService paginationService;

  /** Instantiate pagination object. */
  @Autowired
  PaginationController(PaginationService paginationService) {
    this.paginationService = paginationService;
  }

  /**
   * Retrieves records from the current page.
   *
   * @return {@code List<BookDto>} books.
   */
  @GetMapping("/books/pagination")
  ResponseEntity<List<BookDto>> getCurrentPage() {
    List<BookDto> books = paginationService.currentPage();
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
    List<BookDto> books = paginationService.nextPage();
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
    List<BookDto> books = paginationService.previousPage();
    return new ResponseEntity<>(books, HttpStatus.OK);
  }
}
