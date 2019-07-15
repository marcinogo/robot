package edition.academy.seventh.controller;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.serivce.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** @author Kamil Rojek */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
class BookController {
  private BooksService booksService;

  @Autowired
  public BookController(BooksService booksService) {
    this.booksService = booksService;
  }

  /**
   * Gets the list of the books.
   *
   * @return List of books.
   */
  @GetMapping("/books")
  public List<Book> getBooks() {
    return booksService.getBooksFromDataBase();
  }
}
