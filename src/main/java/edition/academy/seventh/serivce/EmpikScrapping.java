package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Scrap data from empik.com bookstore website in sales section using JSoup library.
 *
 * @author Bartosz Kupajski
 */
public class EmpikScrapping implements IPromotionScrapping {

    private List<Book> listOfBooks = new CopyOnWriteArrayList<>();
    private ExecutorService service = Executors.newFixedThreadPool(40);
    private Phaser phaser = new Phaser(1);
    private static final String START_OF_URL = "https://www.empik.com/promocje?searchCategory=31&hideUnavailable=true&start=";
    private static final String END_OF_URL = "&qtype=facetForm";

    /**
        Above that number SSLException is thrown
     */
    private static final int MAX_CONNECTIONS = 1201;

  /**
   * @return list of mapped books
   */
  @Override
  public List<Book> scrapPromotion() {

      for (int i = 1; i <= MAX_CONNECTIONS; i = i + 30) {
        service.submit(createScrappingTask(i));
        }

        phaser.arriveAndAwaitAdvance();
      return listOfBooks;
    }

  private Runnable createScrappingTask(int serchSiteNumber) {
    return () -> {
      phaser.register();
      String url = createUrl(serchSiteNumber);
      Document document = null;
      try {
        document = Jsoup.connect(url).get();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
      Elements elementsByClass =
          document.getElementsByClass("product-details-wrapper ta-details-box");

      mappingToBookList(listOfBooks, elementsByClass);
    };
  }

  private String createUrl(int i) {
    return START_OF_URL + i + END_OF_URL;
  }

  private void mappingToBookList(List<Book> listOfBooks, Elements elementsByClass) {
    elementsByClass.stream()
        .map(
            element -> {
              String title = element.getElementsByClass("ta-product-title").text();
              String author = element.getElementsByClass("smartAuthor").text();
              String promotionPrice = element.getElementsByClass("ta-price-tile").text();
              String[] prices = promotionPrice.split(" ");
              Book book = new Book();
              book.setTitle(title);
              book.setAuthors(author);
              book.setSubtitle("");
              book.setPrice(prices[2]);
              book.setPromotion(prices[0]);
              return book;
            })
        .forEach(listOfBooks::add);
        phaser.arrive();
    }
}
