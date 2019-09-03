package edition.academy.seventh.scrapping;

import edition.academy.seventh.pagination.Observer;
import edition.academy.seventh.pagination.PaginationRepository;
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
class ScrapperService implements SubjectOfObservation {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScrapperService.class);

  private PromotionProviderManager providerManager;
  private BookService bookService;
  private PaginationRepository paginationRepository;
  private List<Observer> observerList;

  @Autowired
  ScrapperService(
      PromotionProviderManager providerManager,
      BookService bookService,
      PaginationRepository paginationRepository,
      List<Observer> observerList) {
    this.providerManager = providerManager;
    this.bookService = bookService;
    this.paginationRepository = paginationRepository;
    this.observerList = observerList;
  }

  @Override
  public void notifyObservers() {
    for (Observer observer : observerList) {
      observer.update(paginationRepository.createPaginationResult());
      LOGGER.info("Pagination update done: " + observer);
    }
  }

  boolean getDataFromBookstores() {
    updateEnvironmentCredentials();
    try {
      List<BookDto> books = providerManager.getScrappedBooks();
      bookService.addBooksToDatabase(books);
      LOGGER.info("Scrapped books added to database");
    } catch (ProvidersNotFoundException e) {
      LOGGER.error("Couldn't find any promotion provider " + e.getMessage());
    }
    notifyObservers();
    return true;
  }

  private void updateEnvironmentCredentials() {
    try {
      new ProcessBuilder("./check_environment_variables_script.sh").start();
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
  }
}
