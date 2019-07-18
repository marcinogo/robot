package edition.academy.seventh.controller;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.serivce.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Responsible for starting persistence actions. Running is possible either by HTTP request or
 * scheduled action.
 *
 * @author krzysztof.kramarz
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
class RobotController {

  private BookstoreConnectionService bookstoreConnectionService;
  private ItBookMapper itBookMapper;
  private BookService bookService;

  @Autowired
  RobotController(
      BookstoreConnectionService bookstoreConnectionService,
      ItBookMapper itBookMapper,
      BookService bookService) {
    this.bookstoreConnectionService = bookstoreConnectionService;
    this.itBookMapper = itBookMapper;
    this.bookService = bookService;
  }

  /**
   * Starts robot's run.
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
  @Scheduled(cron = "* * * * * *")
  private boolean scheduleRobot() {
    boolean persistItBookStore = startItBookStoreRobot();
    boolean persistEmpik = startEmpikRobot();
    return persistItBookStore && persistEmpik;
  }

  private boolean startItBookStoreRobot() {
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

  private boolean startEmpikRobot() {
    // TODO zmienić na autowired, gdy EmpikScrapper będzie beanem
    PromotionProvider promotionProvider = new EmpikScrapper();
    List<Book> books = promotionProvider.getPromotions();
    bookService.addBooksToDatabase(books);
    // TODO wrpowadzić try catch i zwracać true/false po zrobieni zadania #118
    return true;
  }
}
