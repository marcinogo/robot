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
 * Scrap data from swiatksiazki.pl bookstore website in sales section using JSoup library.*
 *
 * @author Kacper Staszek
 */

public class SwiatKsiazkiScrapping implements IPromotionScrapping {

  private List<Book> listOfBooks = new CopyOnWriteArrayList<>();
  private ExecutorService service = Executors.newFixedThreadPool(40);
  private Phaser phaser = new Phaser(1);
  private static final String START_OF_URL = "https://www.swiatksiazki.pl/Ksiazki/outlet-3255.html?p=";
  private static final String END_OF_URL = "&product_list_limit=30&product_list_mode=grid";

  /**
   * @return list of mapped books
   */
  @Override
  public List<Book> scrapPromotion() {

    for (int i = 1; i <= 10; i++) {
      service.submit(createScrappingTask(i));
    }
    phaser.arriveAndAwaitAdvance();
    return listOfBooks;
  }

  private String createUrl(int i) {

    return START_OF_URL + i + END_OF_URL;
  }

  private void mappingToBook(List<Book> listOfBooks, Elements elementsByClass) {
    elementsByClass.stream()
        .map(element -> {
          String title = element.getElementsByClass("product name product-item-name").text();
          title = deleteOutletSign(title);
          String author = element.getElementsByClass("product author product-item-author").text();
          String promotionPrice = element.getElementsByClass("special-price").text();
          promotionPrice = deleteCurrencyFromPrice(promotionPrice);
          String basePrice = element.getElementsByClass("old-price").text();
          basePrice = deleteCurrencyFromPrice(basePrice);
          Book book = new Book();
          book.setTitle(title);
          book.setAuthors(author);
          book.setPromotion(promotionPrice);
          book.setPrice(basePrice);
          return book;
        }).forEach(listOfBooks::add);
    phaser.arrive();
  }

  private Runnable createScrappingTask(int searchSiteNumber) {
    return () -> {
      phaser.register();
      String url = createUrl(searchSiteNumber);
      Document document = null;
      try {
        document = Jsoup.connect(url).get();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
      Elements elementsByClass =
          document.getElementsByClass("item product product-item");

      mappingToBook(listOfBooks, elementsByClass);
    };
  }

  private String deleteOutletSign(String title) {
    return title.replace("[OUTLET] ", "");
  }

  private String deleteCurrencyFromPrice(String price) {
    return price.replace(" zł", "");
  }
}