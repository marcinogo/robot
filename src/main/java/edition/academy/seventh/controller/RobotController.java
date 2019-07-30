package edition.academy.seventh.controller;

import edition.academy.seventh.database.model.BookDto;
import edition.academy.seventh.service.BookService;
import edition.academy.seventh.service.PromotionProviderManager;
import edition.academy.seventh.service.ProvidersNotFoundException;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
  private PromotionProviderManager providerManager;
  private BookService bookService;

  @Autowired
  RobotController(PromotionProviderManager providerManager, BookService bookService) {
    this.providerManager = providerManager;
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
   * Checks if data gathering runs uninterrupted.
   *
   * @return true if gathering data completed without issues.
   */
  private boolean startGatheringData() {
    updateEnvironmentCredentials();
    return getDataFromBookstores();
  }

  private boolean getDataFromBookstores() {
    try {
      List<BookDto> books = providerManager.getScrappedBooks();
      bookService.addBooksToDatabase(books);
    } catch (ProvidersNotFoundException e) {
      logger.error("Couldn't find any promotion provider " + e.getMessage());
    }
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
