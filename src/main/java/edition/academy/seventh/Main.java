package edition.academy.seventh;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.serivce.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
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

//    Document doc = Jsoup.connect("https://www.empik.com/promocje?searchCategory=31&hideUnavailable=true&start=2881&qtype=facetForm").get();

    IPromotionScrapping iPromotionScrapping = new EmpikScrapping();
    BookstoreConnectionService connectionService =
        context.getBean(BookstoreConnectionService.class);
    List<String> listOfBooksAsString = connectionService.getListOfBooksAsString();

    ItBookMapper bookMapper = context.getBean(ItBookMapper.class);
    List<Book> books = null;

    try {
      long start = System.currentTimeMillis();
      books = iPromotionScrapping.scrapPromotion();
      long end = System.currentTimeMillis();
     // books = bookMapper.mapListOfJson(listOfBooksAsString);
      System.out.println(end-start);

    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    BooksService booksService = context.getBean(BooksService.class);
    booksService.addBooksToDataBase(books);
//    booksService.getBooksFromDataBase().forEach(b -> System.out.println(b.getAuthors()));
  }
}
