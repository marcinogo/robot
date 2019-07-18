package edition.academy.seventh;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.service.BookService;
import edition.academy.seventh.service.PromotionProvider;
import edition.academy.seventh.service.PwnScrapper;
import java.util.List;
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
    PromotionProvider promotionProvider = new PwnScrapper();
    List<Book> books = promotionProvider.getPromotions();
    BookService booksService = context.getBean(BookService.class);
    booksService.addBooksToDatabase(books);
  }
}
