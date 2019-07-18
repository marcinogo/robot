package edition.academy.seventh.controller;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.service.*;
import edition.academy.seventh.service.mapper.ItBookMapper;
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
  private PromotionProviderManager providerManager;
  private ItBookMapper itBookMapper;
  private BookService bookService;

  @Autowired
  RobotController(
      BookstoreConnectionService bookstoreConnectionService,
      PromotionProviderManager providerManager,
      ItBookMapper itBookMapper,
      BookService bookService) {
    this.bookstoreConnectionService = bookstoreConnectionService;
    this.providerManager = providerManager;
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
    return startGatheringData();
  }

  /**
   * Schedules robot run. Starts every 12 hours.
   *
   * @return result of persist action
   */
  @Scheduled(cron = "0 0 */12 * * *")
  private boolean scheduleRobot() {
    return startGatheringData();
  }

  /**
   *  @return true if gathering data complite without issuess
   */
  private boolean startGatheringData() {
    return getDataFromAPI() && getDataFromScrapping();
  }

  private boolean getDataFromAPI() {
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

  private boolean getDataFromScrapping() {
    try {
      List<Book> books = providerManager.getScrappedBooks();
      bookService.addBooksToDatabase(books);
    } catch (ProvidersNotFoundException e) {
      System.err.println(e.getMessage());
    }
    // TODO wrpowadzić try catch i zwracać true/false po zrobieni zadania #118
    return true;
  }
}
