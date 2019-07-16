package edition.academy.seventh;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.serivce.BooksService;
import edition.academy.seventh.serivce.BookstoreConnectionService;
import edition.academy.seventh.serivce.EmpikScrapping;
import edition.academy.seventh.serivce.IPromotionScrapping;
import edition.academy.seventh.serivce.ItBookMapper;
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

    IPromotionScrapping iPromotionScrapping = new EmpikScrapping();
    BookstoreConnectionService connectionService =
        context.getBean(BookstoreConnectionService.class);
    List<String> listOfBooksAsString = connectionService.getListOfBooksAsString();

    ItBookMapper bookMapper = context.getBean(ItBookMapper.class);
    List<Book> books = null;

    books = iPromotionScrapping.scrapPromotion();

    BooksService booksService = context.getBean(BooksService.class);
    booksService.addBooksToDataBase(books);
  }
}
