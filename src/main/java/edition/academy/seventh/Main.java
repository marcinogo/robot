package edition.academy.seventh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Entry point for entire app.
 *
 * @author Kamil Rojek
 */
@SpringBootApplication(scanBasePackages = "edition.academy.seventh")
public class Main {
  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
  }
}
