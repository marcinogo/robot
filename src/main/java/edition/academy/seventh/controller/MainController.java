package edition.academy.seventh.controller;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.serivce.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Controller for starting persists actions Starting is possible by HTTP request or schedule action
 *
 * @author krzysztof.kramarz
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
class MainController {

  private BookstoreConnectionService bookstoreConnectionService;
  private ItBookMapper itBookMapper;
  private BookService bookService;

  @Autowired
  MainController(
      BookstoreConnectionService bookstoreConnectionService,
      ItBookMapper itBookMapper,
      BookService bookService) {
    this.bookstoreConnectionService = bookstoreConnectionService;
    this.itBookMapper = itBookMapper;
    this.bookService = bookService;
  }

  /**
   * Controller for starting books persistence
   *
   * @return result of persist action
   */
  @GetMapping("/start")
  public boolean startRobot() {
    return scheduleRobot();
  }

  /**
   * Schedules books persistence
   *
   * @return result of persist action
   */
  boolean scheduleRobot() {
    boolean persistItBookStore = persistItBookStore();
    boolean persistEmpik = persistEmpik();
    return persistItBookStore && persistEmpik;
  }

  private boolean persistItBookStore() {
    List<String> listOfBooksAsString = bookstoreConnectionService.getListOfBooksAsString();
    List<Book> books;

    try {
      books = itBookMapper.mapListOfJson(listOfBooksAsString);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      return false;
    }

    bookService.addBooksToDatabase(books);
    return true;
  }

  private boolean persistEmpik() {
    // TODO zmienić na autowired, gdy EmpikScrapper będzie beanem
    PromotionProvider promotionProvider = new EmpikScrapper();
    List<Book> books = promotionProvider.getPromotions();
    bookService.addBooksToDatabase(books);
    // TODO wrpowadzić try catch i zwracać true/false po zrobieni zadania #118
    return true;
  }
}
