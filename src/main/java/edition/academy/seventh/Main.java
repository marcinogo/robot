package edition.academy.seventh;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.service.BookService;
import edition.academy.seventh.service.PromotionProvider;
import edition.academy.seventh.service.PromotionProviderManager;

import java.util.List;

import edition.academy.seventh.service.ProvidersNotFoundException;
import edition.academy.seventh.service.scrapper.EmpikScrapper;
import edition.academy.seventh.service.scrapper.PwnScrapper;
import edition.academy.seventh.service.scrapper.SwiatKsiazkiScrapper;
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

    PromotionProviderManager promotionProviderManager = new PromotionProviderManager();

    List<PromotionProvider> providers = List.of(new EmpikScrapper(), new PwnScrapper(), new SwiatKsiazkiScrapper());

    promotionProviderManager.registerPromotionProvider(providers);

    try {
      List<Book> promotionsBooks = promotionProviderManager.getScrappedBooks();
      BookService booksService = context.getBean(BookService.class);
      booksService.addBooksToDatabase(promotionsBooks);
    } catch (ProvidersNotFoundException e) {
      System.err.println(e.getMessage());
    }
  }
}
