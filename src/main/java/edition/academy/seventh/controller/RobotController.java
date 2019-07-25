package edition.academy.seventh.controller;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.service.*;
import edition.academy.seventh.service.mapper.ItBookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private static final Logger logger = LoggerFactory.getLogger(RobotController.class);
  private BookstoreConnectionService bookstoreConnectionService;
  private PromotionProviderManager providerManager;
  private ItBookMapper itBookMapper;
  private BookService bookService;
  private DatabaseInitializer databaseInitializer;

  @Autowired
  RobotController(
      BookstoreConnectionService bookstoreConnectionService,
      PromotionProviderManager providerManager,
      ItBookMapper itBookMapper,
      BookService bookService,
      DatabaseInitializer databaseInitializer) {
    this.bookstoreConnectionService = bookstoreConnectionService;
    this.providerManager = providerManager;
    this.itBookMapper = itBookMapper;
    this.bookService = bookService;
    this.databaseInitializer = databaseInitializer;
  }

  /**
   * Starts robot's run.
   *
   * @return result of persist action
   */
  @GetMapping("/start")
  public boolean startRobot() {
    databaseInitializer.populateDatabase();
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
   * Checks if data gathering runs uninterrupted.
   *
   * @return true if gathering data completed without issues.
   */
  private boolean startGatheringData() {
    updateEnvironmentCredentials();
    logger.info("credentials updated");
    return getDataFromAPI() && getDataFromScrapping();
  }

  private boolean getDataFromAPI() {
    List<String> listOfBooksAsString = bookstoreConnectionService.getListOfBooksAsString();
    List<BookDto> books;
    try {
      books = itBookMapper.mapListOfJson(listOfBooksAsString);
    } catch (IOException e) {
      logger.error("Error occurred during mapping JSON to BookDto" + e.getMessage());
      return false;
    }

    bookService.addBooksToDatabase(books);
    return true;
  }

  private boolean getDataFromScrapping() {
    try {
      List<BookDto> books = providerManager.getScrappedBooks();
      bookService.addBooksToDatabase(books);
    } catch (ProvidersNotFoundException e) {
      logger.error("Couldn't find any promotion provider " + e.getMessage());
    }

    // TODO wrpowadzić try catch i zwracać true/false po zrobieni zadania #118
    return true;
  }

  private void updateEnvironmentCredentials() {
    try {
      new ProcessBuilder("./check_environment_variables_script.sh").start();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
}
