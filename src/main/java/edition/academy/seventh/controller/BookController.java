package edition.academy.seventh.controller;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.serivce.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** @author Kamil Rojek */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
class BookController {
  private BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  /**
   * Retrieves list of books from database.
   *
   * @return List of books.
   */
  @GetMapping("/books")
  public ResponseEntity<List<Book>> getBooks() {
    return new ResponseEntity<>(bookService.getBooksFromDatabase(), HttpStatus.OK);
  }
}
