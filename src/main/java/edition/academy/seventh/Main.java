package edition.academy.seventh;

import edition.academy.seventh.service.DatabaseInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entry point for entire app.
 *
 * @author Kamil Rojek
 */
@SpringBootApplication(scanBasePackages = "edition.academy.seventh")
@EnableScheduling
public class Main {
  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Main.class, args);
    context.getBean(DatabaseInitializer.class).populateDatabase();
  }
}
