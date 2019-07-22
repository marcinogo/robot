package edition.academy.seventh.service.scrapper;

import edition.academy.seventh.database.model.DtoBook;
import edition.academy.seventh.service.PromotionProvider;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Scraps data from empik.com bookstore website in sales section using JSoup library.
 *
 * @author Bartosz Kupajski
 */
@Service
public class EmpikScrapper implements PromotionProvider {

  private static final Logger logger = LoggerFactory.getLogger(EmpikScrapper.class);
  private List<DtoBook> listOfBooks = new CopyOnWriteArrayList<>();
  private ExecutorService service = Executors.newFixedThreadPool(40);
  private Phaser phaser = new Phaser(1);
  private int numberOfPhase = 0;

  /**
   * Index in "for" loop is incremented by 30 since every URL page of Empik's sales section contains
   * id which is multiplicity of 30.
   *
   * <p>Loop condition is 30 books per page x 20 pages, can be changed later.
   *
   * @return list of books after all scrapping threads finish their jobs.
   */
  @Override
  public List<DtoBook> getPromotions() {

    for (int i = 1; i <= 30 * 20; i = i + 30) {
      service.submit(createScrappingTask(i));
      logger.info(
          "Submitting scrapping task for page: "
              + "https://www.empik.com/promocje?searchCategory=31&hideUnavailable=true&start="
              + i
              + "&qtype=facetForm");
    }

    try {
      phaser.awaitAdvanceInterruptibly(numberOfPhase, 5000, TimeUnit.MILLISECONDS);
    } catch (TimeoutException | InterruptedException e) {
      logger.error(
          "Could not scrap every page from empik. Anyway - returned what was already scrapped successfully");
    }
    numberOfPhase++;

    return listOfBooks;
  }

  private Runnable createScrappingTask(int numberOfSiteToBeSearched) {
    return () -> {
      phaser.register();
      String url = createUrl(numberOfSiteToBeSearched);
      Document document = null;
      try {
        document = Jsoup.connect(url).timeout(0).get();
      } catch (IOException exception) {
        logger.error(exception.getMessage());
      }
      Elements elementsByClass =
          Objects.requireNonNull(document).getElementsByClass("productWrapper");

      mappingToBookList(elementsByClass);
    };
  }

  private String createUrl(int numberOfPage) {
    String startOfUrl =
        "https://www.empik.com/promocje?searchCategory=31&hideUnavailable=true&start=";
    String endOfUrl = "&qtype=facetForm";
    return startOfUrl + numberOfPage + endOfUrl;
  }

  private void mappingToBookList(Elements elementsByClass) {
    String nameOfTheBookstore = "EMPIK";
    String startOfTheUrl = "https://www.empik.com/";
    elementsByClass.stream()
        .map(
            element -> {
              String title = element.getElementsByClass("ta-product-title").text();
              String href = element.getElementsByClass("seoTitle").attr("href");
              href = startOfTheUrl + href;
              String imageLink = element.getElementsByClass("lazy").attr("lazy-img");
              String author = element.getElementsByClass("smartAuthor").text();
              String prices = element.getElementsByClass("ta-price-tile").text();
              String[] pricesArray = prices.split(" ");
              String retailPrice = pricesArray[0] + " " + pricesArray[1];
              String promotionalPrice = pricesArray[2] + " " + pricesArray[3];
              return new DtoBook(
                  title,
                  "",
                  author,
                  retailPrice,
                  promotionalPrice,
                  imageLink,
                  href,
                  nameOfTheBookstore);
            })
        .forEach(listOfBooks::add);
    phaser.arrive();
  }
}
