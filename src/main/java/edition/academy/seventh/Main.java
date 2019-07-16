package edition.academy.seventh;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.serivce.BooksService;
import edition.academy.seventh.serivce.BookstoreConnectionService;
import edition.academy.seventh.serivce.IPromotionScrapping;
import edition.academy.seventh.serivce.ItBookMapper;
import edition.academy.seventh.serivce.SwiatKsiazkiScrapping;
import java.io.IOException;
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

    IPromotionScrapping iPromotionScrapping = new SwiatKsiazkiScrapping();
    BookstoreConnectionService connectionService =
        context.getBean(BookstoreConnectionService.class);
    List<String> listOfBooksAsString = connectionService.getListOfBooksAsString();

    ItBookMapper bookMapper = context.getBean(ItBookMapper.class);
    List<Book> books = null;

    try {
      books = iPromotionScrapping.scrapPromotion();
      System.out.println("ZESKRAPOWANKO");
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    BooksService booksService = context.getBean(BooksService.class);
    booksService.addBooksToDataBase(books);
  }
}
