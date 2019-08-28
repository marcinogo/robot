package edition.academy.seventh.scrapping;

import edition.academy.seventh.persistence.BookService;
import edition.academy.seventh.persistence.response.BookDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/** @author krzysztof.niedzielski */
@Service
class ScrapperService {

  private static final Logger logger = LoggerFactory.getLogger(ScrapperService.class);

  private PromotionProviderManager providerManager;
  private BookService bookService;

  @Autowired
  ScrapperService(PromotionProviderManager providerManager, BookService bookService) {
    this.providerManager = providerManager;
    this.bookService = bookService;
  }

  boolean getDataFromBookstores() {
    updateEnvironmentCredentials();
    try {
      List<BookDto> books = providerManager.getScrappedBooks();
      bookService.addBooksToDatabase(books);
    } catch (ProvidersNotFoundException e) {
      logger.error("Couldn't find any promotion provider " + e.getMessage());
    }
    return true;
  }

  private void updateEnvironmentCredentials() {
    try {
      new ProcessBuilder("./check_environment_variables_script.sh").start();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
}
