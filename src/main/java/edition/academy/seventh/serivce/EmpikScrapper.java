package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

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

      mappingToBookList(listOfBooks, elementsByClass);
    };
  }

  private String createUrl(int numberOfPage) {
    String startOfUrl =
        "https://www.empik.com/promocje?searchCategory=31&hideUnavailable=true&start=";
    String endOfUrl = "&qtype=facetForm";
    return startOfUrl + numberOfPage + endOfUrl;
  }

  private void mappingToBookList(List<Book> listOfBooks, Elements elementsByClass) {
    String nameOfTheBookstore = "EMPIK";
    elementsByClass.stream()
        .map(
            element -> {
              String title = element.getElementsByClass("ta-product-title").text();
              String href = element.getElementsByClass("seoTitle").attr("href");
              String img = element.getElementsByClass("lazy").attr("lazy-img");
              String author = element.getElementsByClass("smartAuthor").text();
              String promotionPrice = element.getElementsByClass("ta-price-tile").text();
              String[] prices = promotionPrice.split(" ");
                //              book.setTitle(title);
//              book.setAuthors(author);
//              book.setSubtitle("");
//              book.setPrice(prices[2] + " " + prices[3]);
//              book.setPromotion(prices[0] + " " + prices[1]);
//              book.setBookstore(nameOfTheBookstore);
//              book.setHref(href);
//              book.setImg(img);
              return new Book(title,author,prices[2] + " " + prices[3], prices[0] + " " + prices[1],href,img, nameOfTheBookstore);
            })
        .forEach(listOfBooks::add);
    phaser.arrive();
  }
}
