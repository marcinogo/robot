package edition.academy.seventh;

import edition.academy.seventh.bookstoreconnectionservice.BookstoreConnectionService;
import edition.academy.seventh.bookstoreconnectionservice.ItbookBookstoreConnectionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "edition.academy.seventh")
public class Main {
  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = SpringApplication.run(Main.class, args);
    BookstoreConnectionService bookstoreConnectionService = applicationContext.getBean(ItbookBookstoreConnectionService.class);
    bookstoreConnectionService.getListOfBooksAsString();
  }
}
