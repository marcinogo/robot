package edition.academy.seventh.service;

import edition.academy.seventh.database.model.Book;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Scraps data from empik.com bookstore website in sales section using JSoup library.
 *
 * @author Bartosz Kupajski
 */
public class EmpikScrapper implements PromotionProvider {

  private List<Book> listOfBooks = new CopyOnWriteArrayList<>();
  private ExecutorService service = Executors.newFixedThreadPool(40);
  private Phaser phaser = new Phaser(1);
  private int numberOfPhase = 0;

  /**
   * Index in for loop is incremented by 30 because of the fact that every page URL of empik sales
   * section contains id which is incremented by 30.
   *
   * <p>Loop condition is 30 books per page x 20 pages, can be changed later.
   *
   * @return list of books after all threads finish their jobs
   */
  @Override
  public List<Book> getPromotions() {

    for (int i = 1; i <= 30 * 20; i = i + 30) {
      service.submit(createScrappingTask(i));
    }

    try {
      phaser.awaitAdvanceInterruptibly(numberOfPhase, 5000, TimeUnit.MILLISECONDS);
    } catch (TimeoutException | InterruptedException e) {
      System.err.println(
          "Could not scrap every page from empik. Anyway - returned what was already scrapped successfully");
    }
    numberOfPhase++;

    return listOfBooks;
  }

  private Runnable createScrappingTask(int searchSiteNumber) {
    return () -> {
      phaser.register();
      String url = createUrl(searchSiteNumber);
      Document document = null;
      try {
        document = Jsoup.connect(url).timeout(0).get();
      } catch (IOException exception) {
        System.err.println(exception.getMessage());
      }
      Elements elementsByClass = document.getElementsByClass("productWrapper");

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
              String img = element.getElementsByClass("lazy").attr("lazy-img");
              String author = element.getElementsByClass("smartAuthor").text();
              String prices = element.getElementsByClass("ta-price-tile").text();
              String[] pricesArray = prices.split(" ");
              String basePrice = pricesArray[0] + " " + pricesArray[1];
              String promotionPrice = pricesArray[2] + " " + pricesArray[3];
              return new Book(title, "", author, basePrice, promotionPrice, img, href,
                  nameOfTheBookstore);
            })
        .forEach(listOfBooks::add);
    phaser.arrive();
  }
}
