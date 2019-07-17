package edition.academy.seventh.controller;

import edition.academy.seventh.database.model.DTBook;
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
   * Gets the list of the books.
   *
   * @return List of books.
   */
  @GetMapping("/books")
  public ResponseEntity<List<DTBook>> getBooks() {
    return new ResponseEntity<>(bookService.getBooksFromDataBase(), HttpStatus.OK);
  }
}
