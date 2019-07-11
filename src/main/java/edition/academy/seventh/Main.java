package edition.academy.seventh;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.serivce.BooksService;
import edition.academy.seventh.serivce.BookstoreConnectionService;
import edition.academy.seventh.serivce.ItBookMapper;
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

    BookstoreConnectionService connectionService = context.getBean(BookstoreConnectionService.class);
    List<String> listOfBooksAsString = connectionService.getListOfBooksAsString();

    ItBookMapper bookMapper = context.getBean(ItBookMapper.class);
    List<Book> books = null;

    try {
      books = bookMapper.mapListOfJson(listOfBooksAsString);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    BooksService booksService = context.getBean(BooksService.class);
    booksService.addBooksToDataBase(books);
  }
}
