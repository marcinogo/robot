package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.database.model.Book;
import edition.academy.seventh.service.PromotionProvider;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements sharded behaviour of all Scrapping classes.
 *
 * @author Kacper Staszek
 */
abstract class AbstractScrapper implements PromotionProvider {

  String startOfUrl;
  String endOfUrl;
  static final Logger logger = LoggerFactory.getLogger(AbstractScrapper.class);
  private String documentClassName;

  List<Book> listOfBooks = new CopyOnWriteArrayList<>();
  protected ExecutorService service = Executors.newFixedThreadPool(40);
  Phaser phaser = new Phaser(1);

  AbstractScrapper(String startOfUrl, String endOfUrl, String documentClassName) {
    this.startOfUrl = startOfUrl;
    this.endOfUrl = endOfUrl;
    this.documentClassName = documentClassName;
  }

  /**
   * Create {@link Runnable} task witch is responsible to create GET request
   * to given page based on {@link AbstractScrapper#startOfUrl}
   * and {@link AbstractScrapper#endOfUrl}, then extract all {@link org.jsoup.nodes.Element}
   * of the given name {@link AbstractScrapper#documentClassName}
   *
   * @param numberOfSearchedSite number of site witch is concat to the URL.
   *
   * @return task witch is later use in {@link ExecutorService}.
   */
  Runnable createScrappingTask(int numberOfSearchedSite) {

    return () -> {
      phaser.register();
      String url = getUrlWithPageNumber(numberOfSearchedSite);
      Document document = null;
      try {
        document = Jsoup.connect(url).timeout(0).get();
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
      Elements elementsByClass = Objects.requireNonNull(document)
          .getElementsByClass(documentClassName);

      mappingToBookList(elementsByClass);
    };
  }

  private String getUrlWithPageNumber(int numberOfSearchedSite) {
    return startOfUrl + numberOfSearchedSite + endOfUrl;
  }

  /**
   * Responsible for mapping HTML content to {@link List<Book>}
   *
   * @param elementsByClass main HTML element thant contains all required data.
   */
  abstract void mappingToBookList(Elements elementsByClass);
}
