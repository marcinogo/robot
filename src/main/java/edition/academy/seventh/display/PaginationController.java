package edition.academy.seventh.display;

import edition.academy.seventh.database.connector.ConnectorFactory;
import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edition.academy.seventh.database.connector.DatabaseTypes.POSTGRESQL;

/** @author Kamil Rojek */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
class PaginationController {
  private BookService bookService;
  private Pagination<BookDto> pagination;

  @Autowired
  public PaginationController(BookService bookService) {
    this.bookService = bookService;
    //        this.pagination = new LazyPagination(ConnectorFactory.of(POSTGRESQL));
    this.pagination = new EagerPagination(bookService);
  }

  @GetMapping("/books/pagination")
  public ResponseEntity<List<BookDto>> getBooksWithPagination() {
    List<BookDto> paginationUsingSql = pagination.currentPage();
    return new ResponseEntity<>(paginationUsingSql, HttpStatus.OK);
  }

  @RequestMapping("/books/pagination/size")
  public ResponseEntity<List<BookDto>> changePaginationSize(
      @RequestParam("value") PaginationSize size) {
    List<BookDto> bookDtos = pagination.changePaginationSize(size);
    return new ResponseEntity<>(bookDtos, HttpStatus.OK);
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
  public ResponseEntity<List<BookDto>> setFilter(@RequestParam("type") Filter filter) {
    List<BookDto> bookDtos = pagination.changeFilter(filter);
    return new ResponseEntity<>(bookDtos, HttpStatus.OK);
  }
}
