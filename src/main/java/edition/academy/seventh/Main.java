package edition.academy.seventh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
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
    SpringApplication.run(Main.class, args);
  }
}
