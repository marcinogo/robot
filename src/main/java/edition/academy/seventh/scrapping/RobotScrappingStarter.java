package edition.academy.seventh.scrapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
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
@CrossOrigin("${robot.crossorigin}")
class RobotScrappingStarter {
  private static final Logger LOGGER = LoggerFactory.getLogger(RobotScrappingStarter.class);

  private ScrapperService scrapperService;

  @Autowired
  public RobotScrappingStarter(ScrapperService scrapperService) {
    this.scrapperService = scrapperService;
  }

  /**
   * Starts robot's run.
   *
   * @return result of persist action
   */
  @GetMapping("/start")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity startRobot() {
    new Thread(() -> scrapperService.getDataFromBookstores(), "ScrappingThreadManual").start();
    return new ResponseEntity<>("Started scrapping books", HttpStatus.OK);
  }

  /** Schedules robot run. Starts every 12 hours. */
  @Scheduled(cron = "0 0 */12 * * *")
  void scheduleRobot() {
    new Thread(() -> scrapperService.getDataFromBookstores(), "ScrappingThreadCron").start();
  }
}
