package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.database.model.DtoBook;
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
 * Implements shared behaviour of all Scrapping classes.
 *
 * @author Kacper Staszek
 */
abstract class AbstractScrapper implements PromotionProvider {

  final String startOfUrl;
  final String endOfUrl;
  static final Logger logger = LoggerFactory.getLogger(AbstractScrapper.class);
  private final String documentClassName;

  List<DtoBook> listOfBooks = new CopyOnWriteArrayList<>();
  protected ExecutorService service = Executors.newFixedThreadPool(40);
  Phaser phaser = new Phaser(1);

  AbstractScrapper(String startOfUrl, String endOfUrl, String documentClassName) {
    this.startOfUrl = startOfUrl;
    this.endOfUrl = endOfUrl;
    this.documentClassName = documentClassName;
  }

  /**
   * Create {@link Runnable} task which is responsible for creating GET request
   * to given page based on {@link AbstractScrapper#startOfUrl}
   * and {@link AbstractScrapper#endOfUrl}, then extracting all {@link org.jsoup.nodes.Element}
   * of the given name {@link AbstractScrapper#documentClassName}.
   *
   * @param numberOfSearchedSite number of site which is concatenated to the URL.
   *
   * @return task which is later used in {@link ExecutorService}.
   */
  Runnable createScrappingTask(int numberOfSearchedSite) {

    return () -> {
      phaser.register();
      String url = getUrlWithPageNumber(numberOfSearchedSite);
      Document document = getDocument(url);
      Elements elementsByClass = Objects.requireNonNull(document)
            .getElementsByClass(documentClassName);

      mappingToBookList(elementsByClass);
    };
  }

  /**
   * @param url of website to scrap.
   * @return {@link Document} which will mapped to {@link DtoBook}.
   */
  private Document getDocument(String url) {
    Document document = null;
    try {
      document = Objects.requireNonNull(Jsoup.connect(url).timeout(0).get());
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
    return document;
  }

  private String getUrlWithPageNumber(int numberOfSearchedSite) {
    return startOfUrl + numberOfSearchedSite + endOfUrl;
  }

  /**
   * Responsible for mapping HTML content to {@link List<DtoBook>}.
   *
   * @param elementsByClass main HTML element that contains all required data.
   */
  abstract void mappingToBookList(Elements elementsByClass);
}
