package edition.academy.seventh.controller;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.serivce.BookService;
import edition.academy.seventh.serivce.BookstoreConnectionService;
import edition.academy.seventh.serivce.ItBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/** @author krzysztof.kramarz */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
class MainController {

  private BookstoreConnectionService bookstoreConnectionService;
  private ItBookMapper itBookMapper;
  private BookService bookService;

  @Autowired
  public MainController(
      BookstoreConnectionService bookstoreConnectionService,
      ItBookMapper itBookMapper,
      BookService bookService) {
    this.bookstoreConnectionService = bookstoreConnectionService;
    this.itBookMapper = itBookMapper;
    this.bookService = bookService;
  }

  @GetMapping("/start")
  public boolean startRobot() {
    return scheduleRobot();
  }

  @SuppressWarnings({"WeakerAccess", "BooleanMethodNameMustStartWithQuestion"})
  public boolean scheduleRobot() {
    List<String> listOfBooksAsString = bookstoreConnectionService.getListOfBooksAsString();
    List<Book> books;

    try {
      books = itBookMapper.mapListOfJson(listOfBooksAsString);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      return false;
    }

    bookService.addBooksToDataBase(books);
    return true;
  }
}
