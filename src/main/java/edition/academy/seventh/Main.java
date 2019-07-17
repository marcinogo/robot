package edition.academy.seventh;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.serivce.BookService;
import edition.academy.seventh.serivce.EmpikScrapper;
import edition.academy.seventh.serivce.PromotionProvider;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * Entry point for entire app.
 *
 * @author Kamil Rojek
 */
@SpringBootApplication(scanBasePackages = "edition.academy.seventh")
public class Main {
  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
    PromotionProvider promotionProvider = new EmpikScrapper();
    List<Book> books = promotionProvider.getPromotions();
    BookService booksService = context.getBean(BookService.class);
    booksService.addBooksToDatabase(books);
  }
}
