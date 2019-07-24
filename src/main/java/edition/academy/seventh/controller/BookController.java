package edition.academy.seventh.controller;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.connector.DatabaseTypes;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.display.LazyPagination;
import edition.academy.seventh.display.Filter;
import edition.academy.seventh.service.BookService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** @author Kamil Rojek */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
class BookController {
  private BookService bookService;
  private LazyPagination pagination;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
    this.pagination = new LazyPagination(ConnectorFactory.of(DatabaseTypes.POSTGRESQL));
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

  @GetMapping("/books/pagination")
  public ResponseEntity<List<BookDto>> getBooksWithPagination() {
    List<BookDto> paginationUsingSql = pagination.startPagination();
    return new ResponseEntity<>(paginationUsingSql, HttpStatus.OK);
  }

  @GetMapping("/books/pagination/next")
  public ResponseEntity<List<BookDto>> next() {
    List<BookDto> paginationUsingSql = pagination.nextPage();
    return new ResponseEntity<>(paginationUsingSql, HttpStatus.OK);
  }

  @GetMapping("/books/pagination/previous")
  public ResponseEntity<List<BookDto>> previous() {
    List<BookDto> paginationUsingSql = pagination.previousPage();
    return new ResponseEntity<>(paginationUsingSql, HttpStatus.OK);
  }

  @RequestMapping("/books/pagination/filter")
  public ResponseEntity<List<BookDto>> setPriceFilter(@RequestParam("type") Filter filter) {
    List<BookDto> bookDtos = pagination.changeFilter(filter);
    return new ResponseEntity<>(bookDtos, HttpStatus.OK);
  }
}
