package edition.academy.seventh.serivce;

import edition.academy.seventh.database.model.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * Scraps data from pwn bookstore website in sales section using Jsoup library
 *
 * @author Bartosz Kupajski
 */
public class PwnScrapper implements PromotionProvider {

  private List<Book> listOfBooks = new CopyOnWriteArrayList<>();
  private ExecutorService service = Executors.newFixedThreadPool(40);
  private Phaser phaser = new Phaser(1);

  /**
   * Scraps 96 positions for each iteration.
   *
   * @return list of books after all threads finish their jobs
   */
  @Override
  public List<Book> getPromotions() {

    for (int i = 1; i <= 2; i++) {
      service.submit(createScrappingTask(i));
    }

    phaser.arriveAndAwaitAdvance();
    return listOfBooks;
  }

  private Runnable createScrappingTask(int searchSiteNumber) {
      String startOfUrl = "https://ksiegarnia.pwn.pl/promocje?limit=96&vt=list&page=";
    return () -> {
      phaser.register();
      String url = startOfUrl + searchSiteNumber;
      Document document = null;
      try {
        document = Jsoup.connect(url).timeout(0).get();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
      Elements elementsByClass = document.getElementsByClass("emp-product-tile-list");

      mappingToBookList(listOfBooks, elementsByClass);
    };
  }

  private void mappingToBookList(List<Book> listOfBooks, Elements elementsByClass) {
    elementsByClass.stream()
        .map(
            element -> {
              String href = element.getElementsByClass("titleLink").attr("href");
              String title = element.getElementsByClass("emp-info-title").text();
              String author = element.getElementsByClass("emp-info-authors").text();
              String img = element.getElementsByTag("img").attr("src");
              author = deleteAuthorTag(author);
              String promotionPrice = element.getElementsByClass("emp-sale-price-value").text();
              promotionPrice = deleteCurrencyFromPrice(promotionPrice);
              String basePrice = element.getElementsByClass("emp-base-price").text();
              basePrice = deleteCurrencyFromPrice(basePrice);
              Book book = new Book();
              book.setTitle(title);
              book.setAuthors(author);
              book.setPrice(basePrice);
              book.setPromotion(promotionPrice);
              book.setHref(href);
              book.setImg(img);
              book.setBookstore("PWN");
              return book;
            })
        .forEach(listOfBooks::add);
    phaser.arrive();
  }

  private String deleteAuthorTag(String author) {
    return author.replace("Autor: ", "");
  }

  private String deleteCurrencyFromPrice(String price) {
    return price.replace(" zł", "");
  }
}
